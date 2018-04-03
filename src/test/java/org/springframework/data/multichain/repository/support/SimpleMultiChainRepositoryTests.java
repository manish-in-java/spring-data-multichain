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

package org.springframework.data.multichain.repository.support;

import org.junit.Test;
import org.springframework.data.multichain.mapping.model.SimpleMultiChainPersistentEntity;
import org.springframework.data.util.ClassTypeInformation;

/**
 * Unit tests for {@link SimpleMultiChainRepository}.
 */
public class SimpleMultiChainRepositoryTests
{
  /**
   * Tests that a repository cannot be constructed without providing metadata
   * for the entities it manages.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutEntityInformation()
  {
    new SimpleMultiChainRepository<>(null, null);
  }

  /**
   * Tests that a repository cannot be constructed without a MultiChain
   * client as it would be unable to persist the managed entities.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutMultiChainClient()
  {
    new SimpleMultiChainRepository<>(new MultiChainEntityInformation<>(new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Foo.class)))
        , null);
  }
}

/**
 * A domain entity.
 */
class Foo
{
}
