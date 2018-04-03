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

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.isTrue;

/**
 * Configures a {@link MultiChainTemplate} that can be used to communicate with
 * the MultiChain blockchain using a MultiChain RPC server.
 */
public class MultiChainClientFactory
{
  private String  host;
  private String  password;
  private int     port;
  private boolean secure;
  private String  username;

  /**
   * Sets the DNS name or IP address of the host machine for the MultiChain RPC
   * server to connect to.
   *
   * @param host The DNS name or IP address of the host machine for the
   *             MultiChain RPC server to connect to.
   */
  public void setHost(final String host)
  {
    hasText(host, "MultiChain RPC server host name must not be blank.");

    this.host = host;
  }

  /**
   * Sets the password to use for connecting to the MultiChain RPC server.
   *
   * @param password The password to use for connecting to the MultiChain
   *                 RPC server.
   */
  public void setPassword(final String password)
  {
    hasText(password, "MultiChain RPC password must not be blank.");

    this.password = password;
  }

  /**
   * Sets the TCP port number for the MultiChain RPC server to connect to.
   *
   * @param port The TCP port number for the MultiChain RPC server to
   *             connect to.
   */
  public void setPort(final int port)
  {
    isTrue(port > 0, "MultiChain RPC server port must be greater than zero.");

    this.port = port;
  }

  /**
   * Sets whether the MultiChain RPC server accepts connections over a secure
   * channel only (SSL).
   *
   * @param secure Whether the MultiChain RPC server accepts connections over
   *               a secure channel only (SSL).
   */
  public void setSecure(final boolean secure)
  {
    this.secure = secure;
  }

  /**
   * Sets the username to use for connecting to the MultiChain RPC server.
   *
   * @param username The username to use for connecting to the MultiChain
   *                 RPC server.
   */
  public void setUsername(final String username)
  {
    hasText(username, "MultiChain RPC username must not be blank.");

    this.username = username;
  }

  /**
   * Gets a {@link MultiChainClient} that can be used to communicate with a
   * MultiChain RPC server.
   *
   * @return A {@link MultiChainClient}.
   */
  MultiChainClient getMultiChainClient()
  {
    return new MultiChainTemplate(host, port, username, password, secure);
  }
}
