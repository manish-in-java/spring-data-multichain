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
package org.springframework.data.multichain;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Contract and utility methods for a unit test.
 */
public interface UnitTest
{
  Random RANDOM     = new SecureRandom();
  Gson   SERIALIZER = new Gson();

  /**
   * Gets a random integer between {@code 1} and {@code 10}, both inclusive.
   *
   * @return A random integer between {@code 1} and {@code 10}.
   */
  default int getInt()
  {
    return getInt(1, 10);
  }

  /**
   * Gets a random integer between a minimum and a maximum value, including the
   * specified maximum and minimum values.
   *
   * @param minimum The minimum value.
   * @param maximum The maximum value.
   * @return A random integer between the {@code minimum} and {@code maximum},
   * both inclusive.
   */
  default int getInt(final int minimum, final int maximum)
  {
    return minimum + RANDOM.nextInt(maximum - minimum + 1);
  }

  /**
   * Gets a {@link String} generated from 128 random bits.
   *
   * @return A randomly generated {@link String}.
   */
  default String getString()
  {
    return getString(128);
  }

  /**
   * Gets a {@link String} generated from a specified number of random bits.
   *
   * @param bits The number of bits in the {@link String} to generate.
   * @return A randomly generated {@link String}.
   */
  default String getString(final int bits)
  {
    return new BigInteger(bits, RANDOM).toString(16);
  }
}
