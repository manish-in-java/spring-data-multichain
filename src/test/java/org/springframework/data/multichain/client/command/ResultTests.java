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

package org.springframework.data.multichain.client.command;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import org.junit.Test;
import org.springframework.data.multichain.client.support.UnixTimestampDateAdapter;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for {@link Result}.
 */
public class ResultTests implements CommandTest
{
  /**
   * Tests that a result can be deserialized correctly.
   */
  @Test
  public void testDeserialize()
  {
    final FooResult subject = deserialize(String.format("{ \"txid\" : \"%s\", \"blocktime\" : %d }"
        , getString()
        , new Date().getTime() / 1000)
        , FooResult.class);

    assertNotNull(subject);
    assertNotNull(subject.id);
    assertNotNull(subject.time);
  }
}

/**
 * Represents a result of some RPC call.
 */
class FooResult extends Result
{
  @SerializedName("txid")
  String id;

  @JsonAdapter(UnixTimestampDateAdapter.class)
  @SerializedName("blocktime")
  Date time;
}
