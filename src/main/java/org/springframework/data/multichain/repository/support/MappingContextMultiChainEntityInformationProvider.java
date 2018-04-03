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

import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.multichain.mapping.MultiChainPersistentProperty;
import org.springframework.data.multichain.repository.MultiChainEntityInformationProvider;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * Provides metadata about a domain entity persisted to a MultiChain data
 * stream using a {@link MappingContext}.
 */
public class MappingContextMultiChainEntityInformationProvider implements MultiChainEntityInformationProvider
{
  private final MappingContext<? extends MultiChainPersistentEntity<?>, MultiChainPersistentProperty> context;

  /**
   * Sets the {@link MappingContext} to use for looking up persistentEntity metadata.
   *
   * @param context A {@link MappingContext}.
   */
  public MappingContextMultiChainEntityInformationProvider(final MappingContext<? extends MultiChainPersistentEntity<?>, MultiChainPersistentProperty> context)
  {
    Assert.notNull(context, "MappingContext must not be null!");

    this.context = context;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T, ID extends Serializable> MultiChainEntityInformation<T, ID> getEntityInformation(final Class<T> domainClass)
  {
    final MultiChainPersistentEntity<T> persistentEntity = (MultiChainPersistentEntity<T>) context.getPersistentEntity(domainClass);

    Assert.notNull(persistentEntity, "Not a managed type: " + domainClass);

    return new MultiChainEntityInformation<>(persistentEntity);
  }
}
