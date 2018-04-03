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

package org.springframework.data.multichain.repository;

import org.springframework.data.multichain.repository.support.MultiChainEntityInformation;

/**
 * Contract for determining metadata about a domain entity persisted to a
 * MultiChain data stream.
 */
public interface MultiChainEntityInformationProvider
{
  /**
   * Gets metadata about a domain entity, provided its Java type.
   *
   * @param domainClass The Java type for the domain entity.
   * @param <T>         The type of domain entity.
   * @return A {@link MultiChainEntityInformation}.
   */
  <T> MultiChainEntityInformation<T> getEntityInformation(final Class<T> domainClass);
}
