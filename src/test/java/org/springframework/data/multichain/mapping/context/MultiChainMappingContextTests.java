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

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.model.Property;
import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.multichain.mapping.MultiChainPersistentEntity;
import org.springframework.data.multichain.mapping.MultiChainPersistentProperty;
import org.springframework.data.util.ClassTypeInformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link MultiChainMappingContext}.
 */
@RunWith(MockitoJUnitRunner.class)
public class MultiChainMappingContextTests
{
  @Mock
  private SimpleTypeHolder holder;

  private static MultiChainMappingContext        context;
  private static MultiChainPersistentEntity<Foo> persistentEntity;
  private static Property                        property;

  /**
   * Sets up objects required to run the tests.
   */
  @BeforeClass
  public static void setup() throws NoSuchFieldException
  {
    context = new MultiChainMappingContext();

    persistentEntity = getPersistentEntity();

    property = Property.of(persistentEntity.getTypeInformation(), Foo.class.getDeclaredField("baz"));
  }

  /**
   * Tests that metadata about a persistent entity can be generated correctly
   * from its type.
   */
  @Test
  public void testCreatePersistentEntity()
  {
    assertNotNull(persistentEntity);
    assertNotNull(persistentEntity.getType());
    assertEquals(Foo.class, persistentEntity.getType());
  }

  /**
   * Tests that metadata about a persistent property can be generated correctly
   * from its type.
   */
  @Test
  public void testCreatePersistentProperty()
  {
    final MultiChainPersistentProperty subject = context.createPersistentProperty(property
        , persistentEntity
        , holder);

    assertNotNull(subject);
    assertEquals(property.getName(), subject.getName());
  }

  /**
   * Gets a {@link MultiChainPersistentEntity} to run the tests.
   *
   * @return A {@link MultiChainPersistentEntity}.
   */
  private static MultiChainPersistentEntity<Foo> getPersistentEntity()
  {
    return context.createPersistentEntity(ClassTypeInformation.from(Foo.class));
  }
}

/**
 * A domain entity.
 */
class Foo
{
  @Id
  private String bar;
  private String baz;
}
