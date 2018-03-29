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
package org.springframework.data.multichain.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Contract for accessing entities in a MultiChain data stream.
 *
 * @param <T> The type of entities.
 */
@NoRepositoryBean
public interface MultiChainRepository<T> extends Repository<T, String>
{
  /**
   * Finds whether an entity with a given logical key exists.
   *
   * @param key The logical key of the entity to check; must not be
   *            {@literal null}.
   * @return {@literal true} if an entity with the given key exists,
   * {@literal false} otherwise.
   * @throws IllegalArgumentException if {@code key} is {@literal null}.
   */
  boolean existsByKey(String key);

  /**
   * Finds an entity by its logical key.
   *
   * @param key The logical key of the entity to find; must not be
   *            {@literal null}.
   * @return The entity with the given key or {@literal null} if none
   * found.
   * @throws IllegalArgumentException if {@code id} is {@literal null}.
   */
  T findByKey(String key);

  /**
   * Saves a given entity. Use the returned instance for further operations as
   * the operation might have changed the entity instance completely.
   *
   * @param entity The entity to save.
   * @return The saved entity.
   */
  <S extends T> S save(S entity);

  /**
   * Saves all given entities.
   *
   * @param entities The entities to save.
   * @return The saved entities.
   * @throws NullPointerException if {@code entities} is {@literal null}.
   */
  <S extends T> Iterable<S> save(Iterable<S> entities);
}
