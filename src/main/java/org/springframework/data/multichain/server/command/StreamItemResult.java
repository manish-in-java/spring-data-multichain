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

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.multichain.server.support.UnixTimestampDateAdapter;

import java.util.Date;

/**
 * Result of fetching details for an item in a particular data stream on the
 * blockchain.
 */
abstract class StreamItemResult extends Result
{
  private static final long serialVersionUID = 1L;

  private String data;
  @SerializedName("txid")
  private String id;
  private String key;
  @JsonAdapter(UnixTimestampDateAdapter.class)
  @SerializedName("blocktime")
  private Date   time;

  /**
   * Gets the item data. This is a hexadecimal representation of the
   * binary version of the data.
   *
   * @return The item data.
   */
  public String getData()
  {
    return data;
  }

  /**
   * Gets the unique item identifier.
   *
   * @return The unique item identifier.
   */
  public String getID()
  {
    return id;
  }

  /**
   * Gets the logical key associated with the data.
   *
   * @return The logical key associated with the data.
   */
  public String getKey()
  {
    return key;
  }

  /**
   * Gets the date and time at which the item was added to the blockchain.
   *
   * @return The date and time at which the item was added to the blockchain.
   */
  public Date getTime()
  {
    return time;
  }
}
