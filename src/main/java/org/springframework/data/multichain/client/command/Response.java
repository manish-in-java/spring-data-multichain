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

/**
 * Represents a response to a JSON-RPC call to a MultiChain RPC server.
 */
public abstract class Response<T> extends Message
{
  private static final long serialVersionUID = 1L;

  private ResponseError error;
  private T             result;

  /**
   * Gets an optional error encountered in response to the RPC call.
   *
   * @return An optional error encountered in response to the RPC call.
   */
  public ResponseError getError()
  {
    return error;
  }

  /**
   * Gets the result(s) of the RPC call, if it completed successfully.
   *
   * @return The result(s) of the RPC call, if it completed successfully.
   */
  public T getResult()
  {
    return result;
  }
}
