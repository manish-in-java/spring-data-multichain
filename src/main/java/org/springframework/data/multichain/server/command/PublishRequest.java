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

package org.springframework.data.multichain.server.command;

import static org.springframework.util.Assert.hasText;

/**
 * Request for publishing an item to a particular data stream on the blockchain
 * and tagged with a specified key. The data stream name, the key for the item
 * to publish and a hexadecimal version of the JSON representation of the item
 * are included in the request.
 */
public class PublishRequest extends Request<Object[]>
{
  private static final long serialVersionUID = 1L;

  private final String data;
  private final String key;
  private final String stream;

  /**
   * Creates a request for publishing an item to a particular data stream on
   * the blockchain and tagged with a specified key.
   *
   * @param stream The name of the data stream to which the item should be
   *               published.
   * @param key    The logical key for the item to publish.
   * @param data   Hexadecimal {@link String} version of the JSON
   *               representation of the item to publish.
   * @throws IllegalArgumentException if {@code stream}, {@code key} or
   *                                  {@code data} is blank.
   */
  public PublishRequest(final String stream, final String key, final String data)
  {
    super(Command.publish);

    hasText(stream, "Stream must not be blank.");
    hasText(key, "Key must not be blank.");
    hasText(data, "Data must not be blank.");

    this.data = data;
    this.key = key.trim();
    this.stream = stream.trim();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getParams()
  {
    return new Object[] {
        stream  // The name of the data stream to which the item should be published.
        , key   // The logical key for which the item to publish.
        , data  // Hexadecimal version of the JSON representation of the item to publish.
    };
  }
}
