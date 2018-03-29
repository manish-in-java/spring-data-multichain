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
package org.springframework.data.multichain.mapping;

import org.springframework.data.mapping.model.MutablePersistentEntity;

/**
 * Contract for a domain entity whose instances can be persisted to a
 * MultiChain blockchain as a data stream.
 *
 * @param <T> The actual type of the domain entity.
 */
public interface MultiChainPersistentEntity<T> extends MutablePersistentEntity<T, MultiChainPersistentProperty>
{
  /**
   * Gets the name of the MultiChain data stream to which instances of this
   * entity should be persisted.
   *
   * @return The name of the MultiChain data stream to which instances of this
   * entity should be persisted.
   */
  String getStreamName();
}
