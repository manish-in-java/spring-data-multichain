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

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
   * Finds whether an entity with a given unique identifier exists.
   *
   * @param id The unique identifier for the entity to find; must not be
   *           {@literal null}.
   * @return {@literal true} if an entity with the given identifier exists,
   * {@literal false} otherwise.
   * @throws IllegalArgumentException               if {@code id} is blank.
   * @throws DataRetrievalFailureException          if an error occurs while
   *                                                attempting to find the
   *                                                entity.
   * @throws IncorrectResultSizeDataAccessException if more than one entity
   *                                                with the specified
   *                                                identifier is found.
   */
  boolean exists(String id);

  /**
   * Finds an entity by its unique identifier.
   *
   * @param id The unique identifier for the entity to find; must not be
   *           {@literal null}.
   * @return The entity with the given identifier or {@literal null} if none
   * found.
   * @throws IllegalArgumentException               if {@code id} is blank.
   * @throws DataRetrievalFailureException          if an error occurs while
   *                                                attempting to find the
   *                                                entity.
   * @throws IncorrectResultSizeDataAccessException if more than one entity
   *                                                with the specified
   *                                                identifier is found.
   */
  T findOne(String id);

  /**
   * Saves a given entity. Use the returned instance for further operations as
   * the operation might have changed the entity instance completely.
   *
   * @param entity The entity to save.
   * @return The saved entity.
   * @throws DataIntegrityViolationException if a unique identifier is not
   *                                         available for the entity to
   *                                         save.
   * @throws DuplicateKeyException           if an entity with the specified
   *                                         identifier already exists in
   *                                         the data stream.
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
