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
 * Request for fetching items in a particular data stream on the blockchain
 * and tagged with a specified key. The data stream name and the key for the
 * items to fetch are included in the request.
 */
public class ListStreamKeyItemsRequest extends Request<Object[]>
{
  private static final long serialVersionUID = 1L;

  private final String key;
  private final String stream;

  /**
   * Creates a request for fetching items in a particular data stream on the
   * blockchain and tagged with a specified key.
   *
   * @param stream The name of the data stream to query.
   * @param key    The key for the items to query.
   * @throws IllegalArgumentException if {@code stream} or {@code key} is
   *                                  blank.
   */
  public ListStreamKeyItemsRequest(final String stream, final String key)
  {
    super(Command.liststreamkeyitems);

    hasText(stream, "Stream must not be blank.");
    hasText(key, "Key must not be blank.");

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
        stream  // The name of the data stream from which items should be fetched.
        , key   // The logical key for which items should be fetched.
        , false // Request non-verbose response.
        , 2     // Request maximum of 2 items per key.
    };
  }
}
