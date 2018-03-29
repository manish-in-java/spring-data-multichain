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

package org.springframework.data.multichain.client.support;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link UnixTimestampDateAdapter}.
 */
public class UnixTimestampDateAdapterTests
{
  /**
   * Tests that date values can be read correctly.
   */
  @Test
  public void testRead() throws IOException
  {
    // Find out the current date.
    final Date now = new Date();

    // Remove the millisecond part from the date.
    final long seconds = now.getTime() / 1000;

    // Read the date back.
    final Date subject = new UnixTimestampDateAdapter().read(new JsonReader(new StringReader(String.valueOf(seconds))));

    assertNotNull(subject);
    assertEquals(seconds * 1000, subject.getTime());
  }

  /**
   * Tests that date values can be written correctly.
   */
  @Test
  public void testWrite() throws IOException
  {
    // Find out the current date.
    final Date now = new Date();

    // Write the date.
    final StringWriter writer = new StringWriter();
    new UnixTimestampDateAdapter().write(new JsonWriter(writer), now);

    assertEquals(String.valueOf(now.getTime() / 1000), writer.toString());
  }
}
