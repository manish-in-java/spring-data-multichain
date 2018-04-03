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

package org.springframework.data.multichain.server.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.data.multichain.server.support.MultiChainClientFactoryBean;
import org.w3c.dom.Element;

/**
 * Parses
 * <pre>
 *   <code>
 * <multichain:client host="[MultiChain RPC server host name]"
 *                    port="[MultiChain RPC server port]"
 *                    username="[MultiChain RPC username]"
 *                    password="[MultiChain RPC password]"
 *                    secure="[true|false]"/>
 *   </code>
 * </pre>
 * XML configuration elements.
 */
public class MultiChainClientBeanDefinitionParser extends AbstractBeanDefinitionParser
{
  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext)
  {
    final BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(MultiChainClientFactoryBean.class);
    setProperties(element, builder);

    return getSourcedBeanDefinition(builder, element, parserContext);
  }

  /*
   * (non-Javadoc)
   * Extracts configuration properties required to build a
   * {@code MultiChainTemplate}.
   */
  private void setProperties(final Element element, final BeanDefinitionBuilder builder)
  {
    for (final MultiChainClientProperties property : MultiChainClientProperties.values())
    {
      builder.addPropertyValue(property.name(), element.getAttribute(property.name()));
    }
  }

  /*
   * (non-Javadoc)
   * Creates a {@code MultiChainTemplate} from the configuration information.
   */
  private AbstractBeanDefinition getSourcedBeanDefinition(final BeanDefinitionBuilder builder
      , final Element source
      , final ParserContext context)
  {
    final AbstractBeanDefinition definition = builder.getBeanDefinition();
    definition.setSource(context.extractSource(source));

    return definition;
  }

  /**
   * Names of configuration properties required to build a
   * {@code MultiChainTemplate}.
   */
  private enum MultiChainClientProperties
  {
    host, password, port, secure, username
  }
}
