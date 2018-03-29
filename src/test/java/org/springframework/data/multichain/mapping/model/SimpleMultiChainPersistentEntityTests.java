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

import org.junit.Test;
import org.springframework.data.multichain.annotation.MultiChainStream;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.util.StringUtils;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Unit tests for {@link SimpleMultiChainPersistentEntity}.
 */
public class SimpleMultiChainPersistentEntityTests
{
  /**
   * Tests that entity metadata cannot be determined without knowing the
   * entity type.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructWithoutTypeInformation()
  {
    new SimpleMultiChainPersistentEntity<>(null);
  }

  /**
   * Tests that the name of the MultiChain data stream to which entities of a
   * certain type are persisted can be determined even if the entity class is
   * not annotated.
   */
  @Test
  public void testGetStreamNameWithoutAnnotation()
  {
    assertFalse(StringUtils.isEmpty(new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Alpha.class)).getStreamName()));
  }

  /**
   * Tests that the name of the MultiChain data stream to which entities of a
   * certain type are persisted can be determined even if the stream name has
   * not been specified explicitly for the entity class.
   */
  @Test
  public void testGetStreamNameWithoutStreamName()
  {
    assertEquals(Beta.class.getSimpleName().toLowerCase(Locale.ENGLISH), new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Beta.class)).getStreamName());
  }

  /**
   * Tests that the name of the MultiChain data stream to which entities of a
   * certain type are persisted can be determined from the annotation for an
   * entity class.
   */
  @Test
  public void testGetStreamNameWithStreamName()
  {
    assertEquals(Gamma.class.getSimpleName().toLowerCase(Locale.ENGLISH), new SimpleMultiChainPersistentEntity<>(ClassTypeInformation.from(Gamma.class)).getStreamName());
  }
}

/**
 * A domain entity.
 */
class Alpha
{
}

/**
 * A domain entity.
 */
@MultiChainStream
class Beta
{
}

/**
 * A domain entity.
 */
@MultiChainStream("gamma")
class Gamma
{
}
