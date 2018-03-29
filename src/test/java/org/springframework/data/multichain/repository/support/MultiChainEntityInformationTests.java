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
 *
 */

package org.springframework.data.multichain.repository.support;

import org.junit.Test;
import org.springframework.data.multichain.annotation.MultiChainStream;
import org.springframework.data.multichain.mapping.model.SimpleMultiChainPersistentEntity;
import org.springframework.data.util.ClassTypeInformation;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link MultiChainEntityInformation}.
 */
public class MultiChainEntityInformationTests
{
  /**
   * Tests that entity metadata cannot be determined without specifying the
   * entity type.
   */
  @Test(expected = NullPointerException.class)
  public void testConstructWithoutEntity()
  {
    new MultiChainEntityInformation<>(null);
  }

  /**
   * Tests that the name of the MultiChain data stream to which entities are
   * persisted can be determined from the metadata for the entity type
   * provided by annotations.
   */
  @Test
  public void testGetStreamName()
  {
    assertEquals("customers"
        , new MultiChainEntityInformation<>(new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Customer.class))).getStreamName());
  }
}

/**
 * A domain entity.
 */
@MultiChainStream("customers")
class Customer
{
}
