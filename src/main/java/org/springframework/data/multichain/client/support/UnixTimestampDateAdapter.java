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

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * Deserializes Unix-like timestamps, which are expressed as the number of
 * seconds since {@literal January 1, 1970} to {@link Date}s.
 */
public class UnixTimestampDateAdapter extends TypeAdapter<Date>
{
  private static final int MULTIPLIER = 1000;

  /**
   * Converts a Unix-like timestamp into a {@link Date} by converting it into
   * milliseconds and then into a date.
   *
   * @param reader The {@link JsonReader} to read the timestamp from.
   * @return A {@link Date}.
   * @throws IOException if the timestamp cannot be converted to date.
   */
  @Override
  public Date read(final JsonReader reader) throws IOException
  {
    return new Date(reader.nextLong() * MULTIPLIER);
  }

  /**
   * Converts a {@link Date} into a Unix-like timestamp by reading its
   * value in milliseconds since {@literal January 1, 1970} and converting
   * it into seconds.
   *
   * @param writer The {@link JsonWriter} to write the converted value to.
   * @param date   The {@link Date} to convert.
   * @throws IOException if the date cannot be converted to timestamp.
   */
  @Override
  public void write(final JsonWriter writer, final Date date) throws IOException
  {
    writer.value(date.getTime() / MULTIPLIER);
  }
}
