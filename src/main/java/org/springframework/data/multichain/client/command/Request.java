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
package org.springframework.data.multichain.client.command;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Represents a request to a JSON-RPC call to a MultiChain RPC server.
 */
public abstract class Request<T> extends Message
{
  private static final long serialVersionUID = 1L;

  private static final Random RANDOM = new SecureRandom();

  private final Command command;

  /**
   * Creates a new request with a unique, randomly generated identifier.
   *
   * @param command The MultiChain JSON-RPC API command to invoke.
   * @throws IllegalArgumentException if {@code method} is {@literal null} or
   *                                  blank.
   */
  Request(final Command command)
  {
    super();

    // Ensure that the command name has been specified.
    notNull(command, "Command must not be null.");

    this.command = command;

    setID(getGeneratedID());
  }

  /**
   * Gets the name of the MultiChain JSON-RPC API command to invoke.
   *
   * @return The name of the MultiChain JSON-RPC API command to invoke.
   */
  public final String getCommand()
  {
    return command.name();
  }

  /**
   * Gets optional parameters to be sent along with the request.
   *
   * @return Optional parameters to be sent along with the request.
   */
  public T getParams()
  {
    return null;
  }

  /**
   * Gets a {@link String} identifier generated from {@literal 128} random
   * bits.
   *
   * @return A randomly generated {@link String} identifier.
   */
  private String getGeneratedID()
  {
    return new BigInteger(128, RANDOM).toString(16);
  }

  /**
   * Represents a MultiChain API command.
   */
  protected enum Command
  {
    liststreamkeyitems
  }
}
