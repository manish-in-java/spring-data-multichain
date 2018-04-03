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

import com.google.gson.Gson;
import org.springframework.dao.*;
import org.springframework.data.multichain.repository.MultiChainRepository;
import org.springframework.data.multichain.server.MultiChainClient;
import org.springframework.data.multichain.server.command.ListStreamKeyItemsRequest;
import org.springframework.data.multichain.server.command.ListStreamKeyItemsResponse;
import org.springframework.data.multichain.server.command.PublishRequest;
import org.springframework.data.multichain.server.command.PublishResponse;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

/**
 * Provides <a href="https://martinfowler.com/eaaCatalog/repository.html">Repository</a>-style
 * access to entities stored in a MultiChain data stream.
 *
 * @param <T> The type of entities.
 */
public class SimpleMultiChainRepository<T> implements MultiChainRepository<T>
{
  private final MultiChainEntityInformation<T, String> entityInformation;
  private final MultiChainClient                       multiChainClient;
  private final Gson                                   serializer;

  /**
   * Creates a repository for an entity type using metadata for the type and
   * a {@link MultiChainClient} to use for interacting with the MultiChain
   * RPC server that stores entity data.
   *
   * @param entityInformation Metadata about the entity type for this
   *                          repository.
   * @param multiChainClient  The {@link MultiChainClient} to use for
   *                          persisting entity instances to the MultiChain
   *                          RPC server that stores entity data.
   * @throws IllegalArgumentException if {@code entityInformation} or
   *                                  {@code multiChainClient} is
   *                                  {@literal null}.
   */
  public SimpleMultiChainRepository(final MultiChainEntityInformation<T, String> entityInformation
      , final MultiChainClient multiChainClient)
  {
    notNull(entityInformation, "MultiChainEntityInformation must not be null.");
    notNull(multiChainClient, "MultiChainClient must not be null.");

    this.entityInformation = entityInformation;
    this.multiChainClient = multiChainClient;
    this.serializer = new Gson();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean exists(final String id)
  {
    return findOne(id) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T findOne(final String id)
  {
    hasText(id, "Id must not be blank.");

    // Create a request to find entities tagged with the specified identifier.
    final ListStreamKeyItemsRequest request = new ListStreamKeyItemsRequest(getStreamName(), id.trim());

    // Attempt to find items with the given identifier.
    final ListStreamKeyItemsResponse response = multiChainClient.invoke(request, ListStreamKeyItemsResponse.class);

    // Ensure that no problems were encountered.
    if (!response.isOk())
    {
      throw new DataRetrievalFailureException(response.getError().getMessage());
    }
    // Ensure that not more than 1 entity with the specified identifier was
    // found.
    else if (response.getResult().size() > 1)
    {
      throw new IncorrectResultSizeDataAccessException(1, response.getResult().size());
    }
    // Ensure that at least 1 entity with the specified identifier was found.
    else if (response.getResult().isEmpty())
    {
      return null;
    }

    // Extract entity data, convert it into JSON and deserialize the
    // resulting JSON into an entity.
    return deserialize(response.getResult().iterator().next().getData());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends T> S save(final S entity)
  {
    if (entity == null)
    {
      return null;
    }

    // Extract the unique identifier for the entity.
    final String id = entityInformation.getId(entity);

    // Ensure that the identifier was found.
    if (StringUtils.isEmpty(id))
    {
      throw new DataIntegrityViolationException("Id not available for the entity. Make sure that the entity has a property annotated with @Id.");
    }

    // Ensure that the identifier does not exist already.
    if (exists(id))
    {
      throw new DuplicateKeyException(String.format("Id %s already exists for stream %s.", id, getStreamName()));
    }

    // Create a request for persisting the entity.
    final PublishRequest request = new PublishRequest(getStreamName(), id, serialize(entity));

    // Publish the entity on the blockchain.
    final PublishResponse response = multiChainClient.invoke(request, PublishResponse.class);

    // Ensure that no problems were encountered.
    if (!response.isOk())
    {
      throw new DataAccessResourceFailureException(response.getError().getMessage());
    }

    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <S extends T> Iterable<S> save(final Iterable<S> entities)
  {
    if (entities == null || !entities.iterator().hasNext())
    {
      return Collections.emptyList();
    }

    final List<S> result = new ArrayList<>();
    for (final S entity : entities)
    {
      result.add(save(entity));
    }

    return result;
  }

  /**
   * Deserializes a hexadecimal {@link String} into an entity of the type
   * managed by this repository.
   *
   * @param hex The hexadecimal {@link String} to convert.
   * @return An entity if {@code hex} is not blank, is a valid hexadecimal
   * {@link String}, can be converted into JSON and the JSON is valid for
   * the type managed by this repository, {@code null} otherwise.
   */
  private T deserialize(final String hex)
  {
    return serializer.fromJson(hexToJson(hex), getEntityClass());
  }

  /**
   * Gets the character set to use for converting entities into
   * hexadecimal-coded JSON and vice-versa.
   *
   * @return A {@link Charset}.
   */
  private Charset getCharset()
  {
    return Charset.forName("UTF-8");
  }

  /**
   * Gets the Java type for the entities managed by this repository.
   *
   * @return The Java type for the entities managed by this repository.
   */
  private Class<T> getEntityClass()
  {
    return entityInformation.getJavaType();
  }

  /**
   * Gets the name of the data stream to which entities managed by this
   * repository should be persisted.
   *
   * @return The name of the data stream to which entities managed by this
   * * repository should be persisted.
   */
  private String getStreamName()
  {
    return entityInformation.getStreamName();
  }

  /**
   * Converts a hexadecimal {@link String} into JSON.
   *
   * @param hex The hexadecimal {@link String} to convert.
   * @return A JSON {@link String} if {@code hex} is not blank, is a valid
   * hexadecimal {@link String} and can be converted into JSON,
   * {@literal null} otherwise.
   */
  private String hexToJson(final String hex)
  {
    return hex != null
           ? new String(new BigInteger(hex, 16).toByteArray(), getCharset())
           : null;
  }

  /**
   * Converts a JSON {@link String} into a hexadecimal {@link String}.
   *
   * @param json The JSON {@link String} to convert.
   * @return A hexadecimal {@link String} if {@code json} is not blank,
   * {@literal null} otherwise.
   */
  private String jsonToHex(final String json)
  {
    return json != null
           ? new BigInteger(1, json.getBytes(getCharset())).toString(16)
           : null;
  }

  /**
   * Serializes an entity into a hexadecimal version of its JSON
   * representation.
   *
   * @param entity The entity to serialize.
   * @return Hexadecimal version of the JSON representation of the entity.
   */
  private String serialize(final T entity)
  {
    return jsonToHex(serializer.toJson(entity));
  }
}
