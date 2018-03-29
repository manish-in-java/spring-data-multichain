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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link Request}.
 */
public class RequestTests
{
  /**
   * Tests that a random identifier is automatically generated for a request at
   * the time of instantiation.
   */
  @Test
  public void testGetID()
  {
    final Request first = new SampleRequest();

    assertNotNull(first.getID());

    final Request second = new SampleRequest();

    assertNotNull(second.getID());
    assertNotEquals(first.getID(), second.getID());
  }
}

/**
 * Represents a request to some RPC call.
 */
class SampleRequest extends Request
{
  /*
   * (non-Javadoc)
   */
  SampleRequest()
  {
    super("sample");
  }
}
