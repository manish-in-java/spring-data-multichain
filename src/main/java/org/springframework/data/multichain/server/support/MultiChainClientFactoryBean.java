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
package org.springframework.data.multichain.server.support;

import org.springframework.beans.factory.FactoryBean;

/**
 * Creates a {@link MultiChainTemplate} based on the configuration.
 */
public class MultiChainClientFactoryBean
    extends MultiChainClientFactory
    implements FactoryBean<MultiChainClient>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public MultiChainClient getObject()
  {
    return getMultiChainClient();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getObjectType()
  {
    return MultiChainTemplate.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSingleton()
  {
    return true;
  }
}
