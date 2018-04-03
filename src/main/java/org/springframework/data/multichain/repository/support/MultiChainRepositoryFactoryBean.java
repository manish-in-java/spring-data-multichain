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
package org.springframework.data.multichain.repository.support;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.multichain.server.MultiChainClient;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import static org.springframework.util.Assert.notNull;

/**
 * {@link FactoryBean} for XML and Java based configuration of MultiChain
 * repositories.
 *
 * @param <E> The type of domain entities managed by the repository to create.
 * @param <R> The type of repository to create.
 */
public class MultiChainRepositoryFactoryBean<R extends Repository<E, String>, E>
    extends RepositoryFactoryBeanSupport<R, E, String>
{
  private MultiChainClient multiChainClient;

  /**
   * Sets the {@link Repository} interface for which the implementation needs
   * to be created.
   *
   * @param repositoryInterface A sub-interface of {@link Repository}.
   */
  public MultiChainRepositoryFactoryBean(final Class<? extends R> repositoryInterface)
  {
    super(repositoryInterface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterPropertiesSet()
  {
    super.afterPropertiesSet();

    notNull(multiChainClient, "MultiChainClient must be configured.");
  }

  /**
   * Sets the {@link MultiChainClient} to use for creating the repository
   * implementation.
   *
   * @param multiChainClient A {@link MultiChainClient}.
   */
  public void setMultiChainClient(final MultiChainClient multiChainClient)
  {
    this.multiChainClient = multiChainClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RepositoryFactorySupport createRepositoryFactory()
  {
    return new MultiChainRepositoryFactory(multiChainClient);
  }
}
