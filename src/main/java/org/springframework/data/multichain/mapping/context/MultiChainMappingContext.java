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
package org.springframework.data.multichain.mapping.context;

import org.springframework.data.mapping.context.AbstractMappingContext;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.multichain.mapping.MultiChainPersistentProperty;
import org.springframework.data.multichain.mapping.model.SimpleMultiChainPersistentEntity;
import org.springframework.data.multichain.mapping.model.SimpleMultiChainPersistentProperty;
import org.springframework.data.util.TypeInformation;

/**
 * MultiChain specific implementation of
 * {@link org.springframework.data.mapping.context.MappingContext} that
 * builds generates metadata for persisting domain entities to MultiChain
 * data streams, by creating {@link MultiChainPersistentEntity} and
 * {@link MultiChainPersistentProperty} instances.
 */
public class MultiChainMappingContext extends AbstractMappingContext<MultiChainPersistentEntity<?>, MultiChainPersistentProperty>
{
  /**
   * {@inheritDoc}
   */
  @Override
  protected <T> MultiChainPersistentEntity<T> createPersistentEntity(final TypeInformation<T> typeInformation)
  {
    return new SimpleMultiChainPersistentEntity<>(typeInformation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MultiChainPersistentProperty createPersistentProperty(final Property property
      , final MultiChainPersistentEntity<?> owner
      , final SimpleTypeHolder simpleTypeHolder)
  {
    return new SimpleMultiChainPersistentProperty(property, owner, simpleTypeHolder);
  }
}
