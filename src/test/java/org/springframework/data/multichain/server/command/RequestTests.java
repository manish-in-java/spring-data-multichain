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

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Request}.
 */
public class RequestTests
{
  /**
   * Tests that a request cannot be constructed without specifying the the
   * MultiChain API command to invoke for the request.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutCommand()
  {
    new Request<Void>(null)
    {
    };
  }

  /**
   * Tests that the MultiChain API command name can be retrieved for each
   * request.
   */
  @Test
  public void testGetCommand()
  {
    assertNotNull(new FooRequest().getCommand());
  }

  /**
   * Tests that a random identifier is automatically generated for a request at
   * the time of instantiation.
   */
  @Test
  public void testGetID()
  {
    // Instantiate a request.
    final Request first = new FooRequest();

    // Ensure that an identifier has been generated for the request.
    assertNotNull(first.getID());

    // Instantiate another request.
    final Request second = new FooRequest();

    // Ensure that an identifier has been generated for the other request as
    // well.
    assertNotNull(second.getID());

    // Ensure that the identifiers for the two requests are not the same
    // (assuming that different identifiers also imply randomness).
    assertNotEquals(first.getID(), second.getID());
  }

  /**
   * Tests that additional parameters required to complete a request can be
   * correctly extracted from the request.
   */
  @Test
  public void testGetParams()
  {
    final Request<UUID> subject = new Request<UUID>(Request.Command.liststreamkeyitems)
    {
      @Override
      public UUID getParams()
      {
        return UUID.randomUUID();
      }
    };

    assertNotNull(subject.getParams());
    assertEquals(UUID.class, subject.getParams().getClass());
  }
}

/**
 * Represents a request to some RPC call.
 */
class FooRequest extends Request<String>
{
  /*
   * (non-Javadoc)
   */
  FooRequest()
  {
    super(Command.liststreamkeyitems);
  }
}
