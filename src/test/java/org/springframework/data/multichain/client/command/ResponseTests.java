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

import com.google.gson.JsonSyntaxException;
import org.junit.Test;
import org.springframework.util.StringUtils;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Response}.
 */
public class ResponseTests implements CommandTest
{
  /**
   * Tests that a JSON-RPC response with an error can be deserialized
   * correctly.
   */
  @Test
  public void testDeserializeWithError()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that can be deserialized into a response.
    final String json = String.format("{ \"id\" : %d, \"error\" : { \"code\" : -1, \"message\" : \"General error.\" } }", number);

    // Deserialize the JSON object.
    final SampleResponse subject = deserialize(json, SampleResponse.class);

    assertNotNull(subject);
    assertNotNull(subject.getID());
    assertNull(subject.getResult());

    assertNotNull(subject.getError());
    assertNotEquals(0, subject.getError().getCode());
    assertNotNull(subject.getError().getMessage());
    assertFalse(StringUtils.isEmpty(subject.getError().getMessage()));
  }

  /**
   * Tests that a JSON-RPC response with an invalid error cannot be
   * deserialized correctly.
   */
  @Test(expected = JsonSyntaxException.class)
  public void testDeserializeWithInvalidError()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that cannot be deserialized into a response.
    final String json = String.format("{ \"id\" : %d, \"error\" : [] }", number);

    // Deserialize the JSON object.
    deserialize(json, SampleResponse.class);
  }

  /**
   * Tests that a JSON-RPC response with a result can be deserialized
   * correctly.
   */
  @Test
  public void testDeserializeWithResult()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that can be deserialized into a response.
    final String json = String.format("{ \"id\" : %d, \"error\" : null, \"result\" : \"Ok\" }", number);

    // Deserialize the JSON object.
    final SampleResponse subject = deserialize(json, SampleResponse.class);

    assertNotNull(subject);
    assertNull(subject.getError());
    assertNotNull(subject.getID());

    assertNotNull(subject.getResult());
    assertFalse(StringUtils.isEmpty(subject.getResult()));
  }

  /**
   * Tests that a JSON-RPC response with an uninitialized error can be
   * deserialized correctly.
   */
  @Test
  public void testDeserializeWithUninitializedError()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that can be deserialized into a response.
    final String json = String.format("{ \"id\" : %d, \"error\" : null }", number);

    // Deserialize the JSON object.
    final SampleResponse subject = deserialize(json, SampleResponse.class);

    assertNotNull(subject);
    assertNotNull(subject.getID());
    assertNull(subject.getResult());

    assertNull(subject.getError());
  }

  /**
   * Tests that a JSON-RPC response without an error can be deserialized
   * correctly.
   */
  @Test
  public void testDeserializeWithoutError()
  {
    // Generate a random number.
    final int number = getInt();

    // Generate a JSON object containing the randomly generated number and
    // that can be deserialized into a response.
    final String json = String.format("{ \"id\" : %d }", number);

    // Deserialize the JSON object.
    final SampleResponse subject = deserialize(json, SampleResponse.class);

    assertNotNull(subject);
    assertNotNull(subject.getID());
    assertNull(subject.getResult());

    assertNull(subject.getError());
  }
}

/**
 * Represents a response to some RPC call.
 */
class SampleResponse extends Response<String>
{
}
