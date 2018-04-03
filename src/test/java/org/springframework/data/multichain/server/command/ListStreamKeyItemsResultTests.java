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

package org.springframework.data.multichain.server.command;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link ListStreamKeyItemsResult}.
 */
public class ListStreamKeyItemsResultTests implements CommandTest
{
  /**
   * Tests that a response result can be deserialized correctly.
   */
  @Test
  public void testDeserialize()
  {
    final ListStreamKeyItemsResult subject = deserialize(String.format("{ "
                                                                           + "\"txid\" : \"%s\", "
                                                                           + "\"blocktime\" : %d, "
                                                                           + "\"key\" : \"%s\", "
                                                                           + "\"data\" : \"%s\" "
                                                                           + " }"
        , getString()
        , getTime()
        , getString()
        , getString())
        , ListStreamKeyItemsResult.class);

    assertNotNull(subject);
    assertNotNull(subject.getData());
    assertNotNull(subject.getID());
    assertNotNull(subject.getKey());
    assertNotNull(subject.getTime());
  }
}
