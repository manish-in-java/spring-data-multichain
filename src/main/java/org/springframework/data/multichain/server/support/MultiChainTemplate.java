/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.multichain.server.support;

import com.google.gson.Gson;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.data.multichain.server.MultiChainClient;
import org.springframework.data.multichain.server.command.Request;
import org.springframework.data.multichain.server.command.Response;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

/**
 * Abstraction using which all communication with a MultiChain JSON-RPC server
 * is managed. Using {@link RestTemplate} provided by the Spring framework
 * internally to exchange request and response objects with the RPC server.
 * Serialization and deserialization of Java objects to and from JSON is
 * managed using the Gson library.
 */
public class MultiChainTemplate implements MultiChainClient
{
  private final RestTemplate restTemplate;
  private final Gson         serializer;
  private final URI          uri;

  /**
   * Creates a {@link MultiChainTemplate} that can be used to communicate with
   * a MultiChain RPC server running on a given host and port, using specified
   * username and password.
   *
   * @param host     The DNS name or IP address of the host machine for the
   *                 MultiChain RPC server to connect to.
   * @param port     The TCP port number for the MultiChain RPC server to
   *                 connect to.
   * @param username The username to use for connecting to the MultiChain
   *                 RPC server.
   * @param password The password to use for connecting to the MultiChain
   *                 RPC server.
   * @param secure   Whether the MultiChain RPC server accepts connections over
   *                 a secure channel only (SSL).
   * @throws IllegalArgumentException if {@code host}, {@code username} or
   *                                  {@code password} is {@literal null} or
   *                                  blank, or if {@code port} is invalid.
   */
  public MultiChainTemplate(final String host
      , final int port
      , final String username
      , final String password
      , final boolean secure)
  {
    this(createURI(host, port, secure)
        , createRestTemplate(username, password)
        , new Gson());
  }

  /**
   * Creates a {@link MultiChainTemplate}.
   *
   * @param uri          The {@link URI} to the RPC server.
   * @param restTemplate A {@link RestTemplate} to execute RPC commands on
   *                     the RPC server.
   * @param serializer   A {@link Gson} instance.
   */
  MultiChainTemplate(final URI uri, final RestTemplate restTemplate, final Gson serializer)
  {
    this.restTemplate = restTemplate;
    this.serializer = serializer;
    this.uri = uri;
  }

  /**
   * {@inheritDoc}
   */
  public <Q extends Request, R extends Response<?>> R invoke(final Q request, final Class<R> responseType)
  {
    // Generate JSON-RPC payload for the request.
    final String payload = createRequestPayload(request);

    // Invoke the command and convert the response to the specified response
    // object type.
    return restTemplate.postForObject(uri, payload, responseType);
  }

  /**
   * Creates a {@link ClientHttpRequestFactory} to communicate with a
   * MultiChain RPC server.
   *
   * @param username The username to use for connecting to the MultiChain
   *                 RPC server.
   * @param password The password to use for connecting to the MultiChain
   *                 RPC server.
   * @return A {@link ClientHttpRequestFactory}.
   */
  private static ClientHttpRequestFactory createClientHttpRequestFactory(final String username, final String password)
  {
    return new HttpComponentsClientHttpRequestFactory(createHttpClient(username, password));
  }

  /**
   * Creates an Apache Commons {@link HttpClient} to communicate with a
   * MultiChain RPC server.
   *
   * @param username The username to use for connecting to the MultiChain
   *                 RPC server.
   * @param password The password to use for connecting to the MultiChain
   *                 RPC server.
   * @return A {@link HttpClient}.
   */
  private static HttpClient createHttpClient(final String username, final String password)
  {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

    return HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
  }

  /**
   * Creates a {@link RestTemplate} that can be used to communicate with
   * a MultiChain RPC server, using specified username and password.
   *
   * @param username The username to use for connecting to the MultiChain
   *                 RPC server.
   * @param password The password to use for connecting to the MultiChain
   *                 RPC server.
   * @return A {@link RestTemplate}.
   * @throws IllegalArgumentException if {@code username} or {@code password}
   *                                  is {@literal null} or blank.
   */
  private static RestTemplate createRestTemplate(final String username, final String password)
  {
    // Ensure that the RPC username has been specified.
    hasText(username, "Username] must not be blank.");

    // Ensure that the RPC password has been specified.
    hasText(password, "Password] must not be blank.");

    return new RestTemplate(createClientHttpRequestFactory(username, password));
  }

  /**
   * Creates a {@link URI} for connecting to the MultiChain RPC server.
   *
   * @param host   The DNS name or IP address of the host machine for the
   *               MultiChain RPC server to connect to.
   * @param port   The TCP port number for the MultiChain RPC server to
   *               connect to.
   * @param secure Whether the MultiChain RPC server accepts connections over
   *               a secure channel only (SSL).
   * @return A {@link URI} if {@code host} and {@code port} are in valid
   * format, {@code null} otherwise. No attempt is made to actually connect
   * to the RPC server on the provided host and port.
   * @throws IllegalArgumentException if {@code host} is {@literal null} or
   *                                  if {@code port} is invalid.
   * @throws RuntimeException         if the URI cannot be created (most
   *                                  likely because of the host name being
   *                                  invalid).
   */
  private static URI createURI(final String host, final int port, final boolean secure)
  {
    // Ensure that the host name for the RPC server has been specified.
    hasText(host, "Host must not be null.");

    // Ensure that the RPC server port has been specified.
    isTrue(port > 0, "Port] must be greater than zero.");

    try
    {
      return new URI(String.format("%s://%s:%d", secure ? "https" : "http", host, port));
    }
    catch (final Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * <p>
   * Creates the payload for invoking an API command on the MultiChain RPC
   * server. The payload has the following structure:
   * </p>
   * <pre>
   *   <code>
   *    {
   *      "jsonrpc" : "1.0",
   *      "id"      : "[request identifier]",
   *      "method"  : "[MultiChain API command to invoke]",
   *      "params"  : [additional information to include with the request]
   *    }
   *   </code>
   * </pre>
   * <p>
   * where, {@code jsonrpc} refers to the request protocol (that is, JSON-RPC),
   * {@code 1.0} refers to the JSON-RPC protocol version supported by
   * MultiChain, {@code [request identifier]} is an identifier for the request
   * (when working with a real MultiChain RPC server, this should be unique),
   * {@code [MultiChain API command to invoke]} to the name of the MultiChain
   * command to invoke (for example, {@code publish}, {@code getstreamitem},
   * etc.) and {@code [additional information to include with the request]}
   * refers to any additional information to include with the request for
   * the API command to work successfully (for example, the {@code publish}
   * command expects the name of the MultiChain data stream to publish to,
   * the logical key for the data to publish and the data).
   * </p>
   *
   * @param request A {@link Request} containing information to include in
   *                the request payload.
   * @param <T>     The type of request.
   * @return A JSON string representing the payload to submit to the MultiChain
   * RPC server as part of the request, if {@code request} is not
   * {@literal null}.
   */
  private <T extends Request> String createRequestPayload(final T request)
  {
    if (request == null)
    {
      return null;
    }

    final Map<String, Object> payload = new LinkedHashMap<>();
    payload.put(RequestParameters.PROTOCOL, RequestParameters.VERSION);
    payload.put(RequestParameters.IDENTIFIER, request.getID());
    payload.put(RequestParameters.METHOD, request.getCommand());
    payload.put(RequestParameters.PARAMETERS, request.getParams());

    return serializer.toJson(payload);
  }

  /**
   * Contains JSON-RPC parameters to include in the request to the MultiChain
   * RPC server.
   */
  private final class RequestParameters
  {
    private static final String IDENTIFIER = "id";
    private static final String METHOD     = "method";
    private static final String PARAMETERS = "params";
    private static final String PROTOCOL   = "jsonrpc";
    private static final String VERSION    = "1.0";
  }
}
