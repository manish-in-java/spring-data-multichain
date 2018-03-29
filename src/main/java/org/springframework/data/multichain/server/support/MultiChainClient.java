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

import org.springframework.data.multichain.client.command.Request;
import org.springframework.data.multichain.client.command.Response;

/**
 * Contract for communicating with a MultiChain RPC server.
 */
public interface MultiChainClient
{
  /**
   * Invokes a JSON-RPC command on the MultiChain RPC server, passing it
   * optional information, and converts the response back to a Java object.
   *
   * @param request      A {@link Request} containing additional information
   *                     to include with the command.
   * @param responseType The response type.
   * @param <Q>          The type of request.
   * @param <R>          The type of response.
   * @return The response from the server.
   */
  <Q extends Request, R extends Response<?>> R invoke(Q request, Class<R> responseType);
}
