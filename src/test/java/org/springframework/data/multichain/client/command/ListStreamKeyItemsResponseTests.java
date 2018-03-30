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

import static org.junit.Assert.*;

/**
 * Unit tests for {@link ListStreamKeyItemsResponse}.
 */
public class ListStreamKeyItemsResponseTests implements CommandTest
{
  /**
   * Tests that a response can be deserialized correctly.
   */
  @Test
  public void testDeserialize()
  {
    final ListStreamKeyItemsResponse subject = deserialize(String.format("{ "
                                                                             + " \"id\" : \"%s\", "
                                                                             + " \"error\" : null, "
                                                                             + " \"result\" : [ "
                                                                             + " { "
                                                                             + "   \"txid\" : \"%s\", "
                                                                             + "   \"blocktime\" : %d, "
                                                                             + "   \"key\" : \"%s\", "
                                                                             + "   \"data\" : \"%s\" "
                                                                             + " }, { "
                                                                             + "   \"txid\" : \"%s\", "
                                                                             + "   \"blocktime\" : %d, "
                                                                             + "   \"key\" : \"%s\", "
                                                                             + "   \"data\" : \"%s\" "
                                                                             + " } "
                                                                             + " ]"
                                                                             + " }"
        , getString()
        , getString(), getTime(), getString(), getString()
        , getString(), getTime(), getString(), getString())
        , ListStreamKeyItemsResponse.class);

    assertNotNull(subject);
    assertNull(subject.getError());
    assertNotNull(subject.getID());
    assertNotNull(subject.getResult());
    assertFalse(subject.getResult().isEmpty());

    for (final ListStreamKeyItemsResult result : subject.getResult())
    {
      assertNotNull(result);
      assertNotNull(result.getData());
      assertNotNull(result.getID());
      assertNotNull(result.getKey());
      assertNotNull(result.getTime());
    }
  }
}
