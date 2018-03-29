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

import java.io.Serializable;

/**
 * Represents a message exchanged between a MultiChain RPC server and a client
 * application.
 */
public abstract class Message implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String id;

  /**
   * Gets a (potentially unique) identifier for the message.
   *
   * @return An identifier for the message.
   */
  public String getID()
  {
    return id;
  }

  /**
   * Sets a (potentially unique) identifier for the message.
   *
   * @param id An identifier for the message.
   */
  void setID(final String id)
  {
    this.id = id;
  }
}
