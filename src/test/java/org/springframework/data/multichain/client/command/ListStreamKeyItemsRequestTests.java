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
 * Unit tests for {@link ListStreamKeyItemsRequest}.
 */
public class ListStreamKeyItemsRequestTests implements CommandTest
{
  /**
   * Tests that the API command for the request is
   * {@link Request.Command#liststreamkeyitems}.
   */
  @Test
  public void testGetCommand()
  {
    assertEquals(Request.Command.liststreamkeyitems.name(), new ListStreamKeyItemsRequest(getString(), getString()).getCommand());
  }

  /**
   * Tests that the data stream name and object key are included in the
   * optional information to be included with the request.
   */
  @Test
  public void testGetParams()
  {
    final String key = getString(), stream = getString();

    final Object[] subject = new ListStreamKeyItemsRequest(stream, key).getParams();

    assertNotNull(subject);
    assertEquals(2, subject.length);
    assertEquals(stream, subject[0]);
    assertEquals(key, subject[1]);
  }
}
