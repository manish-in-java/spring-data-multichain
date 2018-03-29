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

import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.model.AnnotationBasedPersistentProperty;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.multichain.mapping.MultiChainPersistentProperty;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * MultiChain specific {@link org.springframework.data.mapping.PersistentProperty}
 * that reads metadata about entity properties to be persisted to the
 * blockchain.
 */
public class SimpleMultiChainPersistentProperty
    extends AnnotationBasedPersistentProperty<MultiChainPersistentProperty>
    implements MultiChainPersistentProperty
{
  /**
   * Creates metadata about a persistent property.
   *
   * @param field              The Java field associated with the property.
   * @param propertyDescriptor Metadata for the accessor method associated
   *                           with the property.
   * @param owner              The entity type for the property.
   * @param simpleTypeHolder   Metadata for the entity type.
   * @throws IllegalArgumentException if {@code owner} or
   *                                  {@code simpleTypeHolder} is
   *                                  {@literal null}.
   */
  public SimpleMultiChainPersistentProperty(final Field field
      , final PropertyDescriptor propertyDescriptor
      , final PersistentEntity<?, MultiChainPersistentProperty> owner
      , final SimpleTypeHolder simpleTypeHolder)
  {
    super(field, propertyDescriptor, owner, simpleTypeHolder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Association<MultiChainPersistentProperty> createAssociation()
  {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAssociation()
  {
    return false;
  }
}
