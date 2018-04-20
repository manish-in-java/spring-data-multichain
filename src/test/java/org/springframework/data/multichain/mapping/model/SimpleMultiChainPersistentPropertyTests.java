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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.util.ClassTypeInformation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Unit tests for {@link SimpleMultiChainPersistentProperty}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SimpleMultiChainPersistentPropertyTests
{
  @Mock
  private SimpleTypeHolder holder;

  private static MultiChainPersistentEntity<?> entity;
  private static Property                      property;

  /**
   * Sets up objects required to run the tests.
   */
  @BeforeClass
  public static void setup() throws NoSuchFieldException
  {
    entity = new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Foo.class));
    property = Property.of(entity.getTypeInformation(), Foo.class.getDeclaredField("bar"));
  }

  /**
   * Tests that a property cannot be used as an association to another
   * entity, since MultiChain does not support associations.
   */
  @Test
  public void testCreateAssociation()
  {
    assertNull(new SimpleMultiChainPersistentProperty(property, entity, holder).createAssociation());
  }

  /**
   * Tests that a property is not treated as an association to another
   * entity, since MultiChain does not support associations.
   */
  @Test
  public void testIsAssociation()
  {
    assertFalse(new SimpleMultiChainPersistentProperty(property, entity, holder).isAssociation());
  }
}

/**
 * A domain entity.
 */
class Foo
{
  private String bar;
}
