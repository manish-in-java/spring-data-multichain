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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link Message}.
 */
public class MessageTests implements CommandTest
{
  /**
   * Tests that a message can be deserialized from JSON.
   */
  @Test
  public void testDeserialization()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that can be deserialized into a message.
    final String json = String.format("{ \"id\" : %d }", number);

    // Deserialize the JSON object.
    final Ping subject = deserialize(json, Ping.class);

    assertNotNull(subject);
    assertNotNull(subject.getID());
    assertEquals(String.valueOf(number), subject.getID());
  }
}

/**
 * Represents a periodic ping.
 */
class Ping extends Message
{
}
