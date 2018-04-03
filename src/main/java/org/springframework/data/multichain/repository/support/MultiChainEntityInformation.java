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

import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.data.repository.core.support.PersistentEntityInformation;

/**
 * MultiChain specific implementation of {@link AbstractEntityInformation}
 * that provides access to metadata about an persistentEntity class so that
 * entity instances can be serialized to and deserialized from a MultiChain
 * data stream.
 *
 * @param <T> The domain persistentEntity type.
 */
public class MultiChainEntityInformation<T> extends PersistentEntityInformation<T, String>
{
  private final MultiChainPersistentEntity<T> persistentEntity;

  /**
   * Creates entity metadata, given the entity type.
   *
   * @param persistentEntity The type of entity for which metadata needs to be
   *                         created.
   * @throws NullPointerException if {@code persistentEntity} is
   *                              {@literal null}.
   */
  public MultiChainEntityInformation(final MultiChainPersistentEntity<T> persistentEntity)
  {
    super(persistentEntity);

    this.persistentEntity = persistentEntity;
  }

  /**
   * Gets the name of the data stream to which entities should be persisted.
   *
   * @return The name of the data stream to which entities should be
   * persisted.
   */
  String getStreamName()
  {
    return persistentEntity.getStreamName();
  }
}
