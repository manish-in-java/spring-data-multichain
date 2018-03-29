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

/**
 * <p>
 * A <a href="https://en.wikipedia.org/wiki/Blockchain">Blockchain</a> is a
 * continuously growing list of records, which are linked sequentially to form
 * a logical chain (hence the name {@code blockchain}). The records are called
 * {@code blocks} and each block stores a cryptographic hash of the immediately
 * preceding block and a timestamp, in addition to user-defined data (which
 * could be anything useful within the context of a software application).
 * The design principles behind blockchains lend them the following features.
 * </p>
 * <ol>
 * <li>There is no restriction on the kind of data that can be stored in each
 * block. It is up to the blockchain implementation to decide what data can be
 * stored in the blocks. This allows blockchains to be used for a wide variety
 * of information storage scenarios.</li>
 * <li>Since each block stores a cryptographic hash of the data in the
 * immediately previous block, each block can verify the authenticity of
 * the data in the previous block. Verification can be performed easily by
 * comparing the cryptographic hash of the data in a given block with that
 * stored in the immediately next block (it is this built-in verification
 * mechanism that lends the name {@code blockchain} to the technology).</li>
 * <li>Blocks can be distributed across multiple physical computing nodes.
 * This brings resilience to the chain, as a single node being unavailable
 * does not render the blockchain unavailable as well.</li>
 * <li>Verifying the chain does not require a central party. Rather,
 * verification is performed through a consensus by majority between the
 * participating computing nodes, that is, a majority of the nodes need
 * to confirm a block as being valid for it to be treated as valid. This
 * allows blockchains to function as decentralized ledgers of information.
 * This feature allows blockchains to be used in scenarios where the
 * participants do not trust each other and therefore cannot simply accept
 * </li>
 * <li>Changing the data in any given block would also change the cryptographic
 * hash for the data in that block. Since each block stores a cryptographic
 * hash of the data contained in the immediately previous block, altering the
 * data in any given block would also require modifying all subsequent blocks,
 * something that cannot be achieved without a majority of the nodes colluding
 * to modify a particular block.</li>
 * </ol>
 * <p>Blockchains can be {@code public} or {@code private} in nature. The
 * former allow any computing node to join and participate in the blockchain
 * network. Public blockchain networks typically encourage more and more
 * participants to join the network by offering incentives for joining. Since
 * anyone can join a public blockchain, it gives rise to the obvious question
 * of whether and how a node can be trusted. For this, ublic blockchains rely
 * on performing a complex, computationally intensive process to achieve
 * consensus among the participating nodes. This process is known as
 * {@code proof-of-work}. Private blockchains on the other hand are controlled
 * by a single party or a group of parties, and require an invitation from
 * one of the existing participants to join.</p>
 * <p>
 * <a href="https://www.multichain.com">MultiChain</a> is an open-source
 * platform for building blockchain solutions. It offers the following
 * benefits.
 * </p>
 * <ul>
 * <li>It is open-source (licensed under GNU Public License), allowing
 * cost-effective solutions to be built.</li>
 * <li>Can be installed as native applications on supported platforms, making
 * ongoing operation (starting, stopping, monitoring ) very easy.</li>
 * <li>Pre-compiled binaries are available for all supported platforms for
 * easy installation.</li>
 * <li>Has a very small footprint.</li>
 * <li>Allows setting up and running a blockchain within minutes.</li>
 * <li>Provides a
 * <a href="https://en.wikipedia.org/wiki/Representational_state_transfer">REST</a>
 * API for accessing and managing the data on a blockchain.</li>
 * </ul>
 * <p>
 * In addition to these benefits, MultiChain also supports {@code Data Streams}
 * on each blockchain. Each {@code stream} is a list of logically similar
 * records. This features allows a MultiChain blockchain to be viewed as a
 * database, and each stream in the blockchain as a logical collection
 * (similar to tables in relational databases).
 * </p>
 * <p>
 * Spring Data MultiChain is a
 * <a href="https://projects.spring.io/spring-data/">Spring Data</a> module
 * that allows applications to interact with MultiChain data streams using a
 * POJO-centric model and a
 * <a href="https://martinfowler.com/eaaCatalog/repository.html">repository-style</a>
 * data-access layer.
 * </p>
 */
package org.springframework.data.multichain;
