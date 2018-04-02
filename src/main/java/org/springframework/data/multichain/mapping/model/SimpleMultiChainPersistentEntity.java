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
package org.springframework.data.multichain.mapping.model;

import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.BasicPersistentEntity;
import org.springframework.data.multichain.annotation.MultiChainStream;
import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.multichain.mapping.MultiChainPersistentProperty;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * Represents a MultiChain {@link PersistentEntity} holding information
 * about the MultiChain data stream to which the entity instances must be
 * persisted.
 *
 * @param <T> The actual type of the domain entity.
 */
public class SimpleMultiChainPersistentEntity<T>
    extends BasicPersistentEntity<T, MultiChainPersistentProperty>
    implements MultiChainPersistentEntity<T>
{
  private final String streamName;

  /**
   * Creates a new instance.
   *
   * @param typeInformation A {@link TypeInformation} containing metadata
   *                        about the actual domain entity to be persisted.
   */
  public SimpleMultiChainPersistentEntity(final TypeInformation<T> typeInformation)
  {
    super(typeInformation);

    this.streamName = extractStreamName(typeInformation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStreamName()
  {
    return streamName;
  }

  /**
   * <p>
   * Derives the name of the MultiChain Data Stream to which the entity
   * instances must be persisted.
   * </p>
   * <ul>
   * <li>First, the {@link MultiChainStream} annotation on the entity class
   * is checked to see if {@link MultiChainStream#name()} has been specified.
   * If yes, the specified name is used.</li>
   * <li>If the name has not been specified, the simple name of the entity
   * class is used. For example, the data stream name for an entity class
   * named {@code Person} will be considered to be {@code person}
   * (all lowercase).</li>
   * </ul>
   *
   * @param typeInformation Metadata about the entity class for which the
   *                        stream name is required.
   * @return The name of the MultiChain Data Stream to which the entity
   * instances must be persisted.
   */
  private String extractStreamName(final TypeInformation<T> typeInformation)
  {
    final MultiChainStream annotation = findAnnotation(MultiChainStream.class);

    // Check if the entity class has the @MultiChain annotation and that
    // the stream name has been specified through the annotation.
    return annotation != null && StringUtils.hasText(annotation.name())
           // Return the stream name specified through the annotation.
           ? annotation.name().trim()
           // Otherwise, return the lowercase version of the entity class's
           // simple name.
           : typeInformation.getType().getSimpleName().toLowerCase(Locale.ENGLISH);
  }
}
