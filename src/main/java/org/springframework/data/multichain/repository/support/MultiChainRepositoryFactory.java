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

import org.springframework.data.multichain.mapping.context.MultiChainMappingContext;
import org.springframework.data.multichain.repository.MultiChainEntityInformationProvider;
import org.springframework.data.multichain.repository.MultiChainRepository;
import org.springframework.data.multichain.server.MultiChainClient;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.io.Serializable;

import static org.springframework.util.Assert.notNull;

/**
 * Creates {@link MultiChainRepository} instances.
 */
public class MultiChainRepositoryFactory extends RepositoryFactorySupport
{
  private final MultiChainEntityInformationProvider entityInformationProvider;
  private final MultiChainClient                    multiChainClient;

  /**
   * Creates an instance with a {@link MultiChainClient} to use for
   * communicating with a MultiChain RPC server.
   *
   * @param multiChainClient A {@link MultiChainClient}; must not be
   *                         {@literal null}.
   * @throws IllegalArgumentException if {@code multiChainClient} is
   *                                  {@literal null}.
   */
  public MultiChainRepositoryFactory(final MultiChainClient multiChainClient)
  {
    notNull(multiChainClient, "MultiChainClient must not be null!");

    this.multiChainClient = multiChainClient;
    this.entityInformationProvider = new MappingContextMultiChainEntityInformationProvider(new MultiChainMappingContext());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T, ID extends Serializable> MultiChainEntityInformation<T, ID> getEntityInformation(final Class<T> domainClass)
  {
    return entityInformationProvider.getEntityInformation(domainClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<?> getRepositoryBaseClass(final RepositoryMetadata repositoryMetadata)
  {
    return SimpleMultiChainRepository.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getTargetRepository(final RepositoryInformation repositoryInformation)
  {
    return getTargetRepositoryViaReflection(repositoryInformation, getEntityInformation(repositoryInformation.getDomainType()), multiChainClient);
  }
}
