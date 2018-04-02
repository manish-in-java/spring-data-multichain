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

package org.springframework.data.multichain.server.support;

import org.junit.Test;
import org.springframework.data.multichain.UnitTest;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link MultiChainTemplate}.
 */
public class MultiChainTemplateTests implements UnitTest
{
  /**
   * Tests that a {@link MultiChainTemplate} can be constructed with valid
   * information.
   */
  @Test
  public void testConstruct()
  {
    assertNotNull(new MultiChainTemplate("127.0.0.1", 9560, getString(), getString(), false));
  }

  /**
   * Tests that a {@link MultiChainTemplate} cannot be constructed without
   * specifying the host name for the RPC server.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutHost()
  {
    new MultiChainTemplate(null, getInt(), getString(), getString(), getBoolean());
  }

  /**
   * Tests that a {@link MultiChainTemplate} cannot be constructed without
   * specifying the password for the RPC server.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutPassword()
  {
    new MultiChainTemplate(getString(), getInt(), getString(), null, getBoolean());
  }

  /**
   * Tests that a {@link MultiChainTemplate} cannot be constructed without
   * specifying the username for the RPC server.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutUsername()
  {
    new MultiChainTemplate(getString(), getInt(), null, getString(), getBoolean());
  }

  /**
   * Tests that a {@link MultiChainTemplate} cannot be constructed without
   * specifying a valid port number for the RPC server.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithInvalidPort()
  {
    new MultiChainTemplate(getString(), -1, getString(), getString(), getBoolean());
  }
}
