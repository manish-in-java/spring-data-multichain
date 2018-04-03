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
package org.springframework.data.multichain.repository.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.data.multichain.annotation.MultiChainStream;
import org.springframework.data.multichain.mapping.context.MultiChainMappingContext;
import org.springframework.data.multichain.repository.MultiChainRepository;
import org.springframework.data.multichain.repository.support.MultiChainRepositoryFactoryBean;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.repository.config.XmlRepositoryConfigurationSource;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;

public class MultiChainRepositoryConfigurationExtension extends RepositoryConfigurationExtensionSupport
{
  /**
   * {@inheritDoc}
   */
  @Override
  protected String getModulePrefix()
  {
    return "multichain";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRepositoryFactoryClassName()
  {
    return MultiChainRepositoryFactoryBean.class.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void postProcess(final BeanDefinitionBuilder builder, final AnnotationRepositoryConfigurationSource config)
  {
    builder.addPropertyReference("multiChainClient", "multiChainClient");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void postProcess(final BeanDefinitionBuilder builder, final XmlRepositoryConfigurationSource config)
  {
    builder.addPropertyReference("multiChainClient", config.getElement().getAttribute("multichain-client-ref"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerBeansForRoot(final BeanDefinitionRegistry registry, final RepositoryConfigurationSource config)
  {
    super.registerBeansForRoot(registry, config);

    RootBeanDefinition definition = new RootBeanDefinition(MultiChainMappingContext.class);
    definition.setRole(AbstractBeanDefinition.ROLE_INFRASTRUCTURE);
    definition.setSource(config.getSource());

    registerIfNotAlreadyRegistered(definition, registry, "multiChainMappingContext", definition);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Collection<Class<? extends Annotation>> getIdentifyingAnnotations()
  {
    return Collections.singleton(MultiChainStream.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Collection<Class<?>> getIdentifyingTypes()
  {
    return Collections.singleton(MultiChainRepository.class);
  }
}
