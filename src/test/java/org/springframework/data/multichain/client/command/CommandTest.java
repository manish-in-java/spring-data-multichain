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

import org.springframework.data.multichain.UnitTest;

/**
 * Contract and utility methods for testing classes used for invoking
 * JSON-RPC commands on a MultiChain RPC server.
 */
public interface CommandTest extends UnitTest
{
  /**
   * Deserializes a JSON string into an object of a specified type.
   *
   * @param json The JSON string to deserialize.
   * @param type The type of object to deserialize to.
   * @param <T>  The type of object to deserialize to.
   * @return An object of the specified type if the JSON string is successfully
   * deserialized.
   */
  default <T> T deserialize(final String json, final Class<T> type)
  {
    return SERIALIZER.fromJson(json, type);
  }
}
