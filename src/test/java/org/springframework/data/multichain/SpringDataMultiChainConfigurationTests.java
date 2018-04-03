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

package org.springframework.data.multichain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.repository.ContractRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Integration tests for loading Spring Data MultiChain repositories.
 */
@RunWith(SpringJUnit4ClassRunner.class)
abstract class SpringDataMultiChainConfigurationTests
{
  @Autowired
  private ContractRepository repository;

  /**
   * Tests that the configuration loads successfully.
   */
  @Test
  public void testConfigLoads()
  {
    assertNotNull(repository);
  }
}
