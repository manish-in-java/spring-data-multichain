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
package org.springframework.data.multichain.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Represents a
 * <a href="https://www.multichain.com/developers/stream-confidentiality/">Data Stream</a>
 * within a <a href="https://www.multichain.com">MultiChain</a> blockchain.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MultiChainStream
{
  /**
   * <p>
   * (Optional) The name of the data stream.
   * </p>
   * <p>
   * Defaults to the simple class name.
   * </p>
   */
  @AliasFor("value")
  String name() default "";

  /**
   * <p>
   * (Optional) The name of the data stream.
   * </p>
   * <p>
   * Defaults to the simple class name.
   * </p>
   */
  @AliasFor("name")
  String value() default "";
}
