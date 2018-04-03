# Spring Data MultiChain

The primary goal of the [Spring Data](http://projects.spring.io/spring-data)
project is to make it easier to build Spring-powered applications that use
new data access technologies such as non-relational databases, map-reduce
frameworks, and cloud-based data services.

The Spring Data MultiChain module provides integration with
[MultiChain](https://www.multichain.com), an open-source platform for
building [blockchain](https://en.wikipedia.org/wiki/Blockchain) solutions.
A blockchain is a continuously growing list of records, which are linked
sequentially to form a logical chain. Each record in the chain stores an
irrefutable cryptographic hash of the immediately preceding block, such
that authenticity of any record can be easily and immediately verified by
computing its cryptographic hash and comparing the computed value against
that stored in the immediately next record.

MultiChain can be used to deploy as many blockchains as required. Ideally,
a new blockchain should be deployed for every unique set of participants
(end-users). MultiChain allows storing different types of data within a
single blockchain as separate
[data streams](https://www.multichain.com/developers/stream-confidentiality/).
Users can apply fine-grained permissions for participants to each data
stream separately. This enables an application to store different types of data
in a MultiChain blockchain, with fine-grained access control, as required.
In addition, MultiChain also provides an
[RPC server](https://www.multichain.com/developers/json-rpc-api/) over each
blockchain, that supports [JSON-RPC](https://en.wikipedia.org/wiki/JSON-RPC)
commands, enabling users and applications to interact with the blockchain
remotely, if required. These features allow using MultiChain blockchains as
structured data stores, with each data stream as a separate data source, and
the RPC server as a remote connectivity solution. As an analogy, a MultiChain
blockchain is the equivalent of a relational database, a data stream is
similar to a relational table in the database, and JSON-RPC is equivalent
to JDBC technology for working with the data in the database.

Key functional areas of this module are a POJO-centric model for interacting
with a MultiChain data stream, and easily writing a repository-style
data-access layer over JSON-RPC.

## 1. Quick Start

### 1.1. Maven configuration

Add the Maven dependency:

```xml
<dependency>
  <groupId>org.springframework.data</groupId>
  <artifactId>spring-data-multichain</artifactId>
  <version>${version}.RELEASE</version>
</dependency>
```

### 1.2. MultiChainTemplate

`MultiChainTemplate` is the central support class for MultiChain JSON-RPC
operations. It provides:

* Basic POJO mapping support to and from JSON;
* Convenience methods to interact with the blockchain (insert and query
objects. **Note**: A blockchain record is generally immutable, therefore,
updating or deleting a record is not supported);
* Exception translation into Spring's [technology agnostic DAO exception
hierarchy](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/dao.html#dao-exceptions).

### 1.3. Domain entities

For the instances of a Java class to be persisted to MultiChain, the following
requirements must be met:

1. The class must be annotated with `@MultiChainStream`; and
1. It must contain a field of type `String` and annotated with `@Id`.

A sample entity is shown below:

```java
@MultiChainStream
public class Foo {
  @Id
  public String id;
}
```

**Note**: Right now, only identifiers of type `String` are supported.

By default, entities are persisted to a data stream having the same name
as the class name in lowercase letters. For example, entities corresponding
to the class shown above will be persisted to a stream named `foo` (lowercase
version of the class name `Foo`).

The stream name can be configured by passing it to the `MultiChainStream`
annotation. All of the following are equivalent:

```java
@MultiChainStream("bar")

@MultiChainStream(name = "bar")

@MultiChainStream(value = "bar")
```

### 1.4. MultiChainRepository

To simplify the creation of data repositories, Spring Data MultiChain provides
a generic repository programming model. It will automatically create a
repository proxy for you that adds implementations of supported methods.

`MultiChainRepository` is the base interface that provides support for working
with MultiChain data streams. This interface provides the following methods:

#### 1.4.1. `T save(T)`

This method saves an entity of type `T`. The saved entity is returned so that
the saved instance can be used by the application.

1. This method looks for the value of the entity field annotated with `@Id`
to find the unique identifier for the entity.
1. It then checks to ensure that the unique identifier does not
already exist within the data stream on the blockchain. If it does, an
exception is thrown.
1. If not, the entity is saved on the blockchain.

#### 1.4.2. `Iterable<T> save(Iterable<T>)`

This method saves multiple entities at the same time and returns them in the
same order in which they were provided.

#### 1.4.3. `T findOne(String)`

This method finds an entity with the specified unique identifier.

1. If the unique identifier is not found in the data stream, `null` is
returned.
1. If multiple records with the unique identifier are found in the data
stream, an exception is thrown as records need to have unique identifiers
(duplication could occur if records are added to the data stream through some
other means).
1. If a single record matching the unique identifier is found, that record
is returned.

#### 1.4.4. `boolean exists(String)`

This method finds if an entity with the specified unique identifier exists
in the data stream.

1. If the unique identifier is not found in the data stream, `false` is
returned.
1. If multiple records with the unique identifier are found in the data
stream, an exception is thrown.
1. If a single record matching the unique identifier is found, `true`
is returned.

### 1.5. Configuration

#### 1.5.1. Java configuration

You can have Spring automatically create proxies for repository interfaces
by using the following Java configuration:

```java
@Configuration
@EnableMultiChainRepositories
class ApplicationConfig {
  @Bean
  public MultiChainClient multiChainClient() {
    return new MultiChainTemplate([host], [port], [username], [password], [secure]);
  }
}
```

where, `[host]` is the DNS name or IP address of the machine on which the
MultiChain RPC server runs (for example `multichain.domain.com`, or
`99.0.0.1`), `[port]` is the TCP port number for the RPC server (for example,
`9560`), `[username]` is the username required to connect to the RPC server
(usually `multichainrpc`, but should be picked up from the file named
`multichain.conf` on the machine running the RPC server), `[password]` is
the password required to connect to the RPC server (should be picked up from
the file named `multichain.conf` on the machine running the RPC server)
and `[secure]` should be set to `true` if the RPC server is behind a proxy
that accepts only SSL connections over HTTPS, or `false` if the RPC server
can be accessed using plain HTTP.

**Note**: The method returning `MultiChainClient` must be named
`multiChainClient` for the default auto-configuration triggered by
`@EnableMultiChainRepositories` to work. If the method is named something
 else (for example, `foo`), the same must be configured as
`@EnableMultiChainRepositories(multiChainClient = "foo")`

#### 1.5.2. XML configuration

The same configuration would look like this in XML:

```xml
<multichain:client id="multiChainClient"
                     host="[host]"
                     port="[port]"
                     username="[username]"
                     password="[password]"
                     secure="[secure]"/>
<multichain:repositories multichain-client-ref="multiChainClient"/>
```

## 2. Example

### 2.1. Domain entity

A sample domain entity whose instances can be persisted to a MultiChain
data stream named `contracts`:

```java
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.multichain.annotation.MultiChainStream;

@MultiChainStream("contracts")
public class Contract {
  @Id
  public String id;

  public String buyer;

  public String seller;

  public BigDecimal price;
}
```

It is the responsibility of the application to provide appropriate identifiers
before attempting to save entities. This is required so that the
application can load entities back from the blockchain using the identifiers.
For this reason, identifiers need to be unique, as otherwise, the entities
cannot be looked up unambiguously.

### 2.2. Repository

The repository interface for the domain entity above:

```java
public interface ContractRepository extends MultiChainRepository<Contract> {}
```

### 2.3. Using the repository

The repository interface can be injected wherever required, provided the
Spring configuration shown above has been completed successfully.

```java
@Service
public class TradeService {
  @Autowired
  private ContractRepository contractRepository;

  public Contract save(final Contract contract) {
    return contractRepository.save(contract);
  }
}
```

## 3. Important points

The following points must be noted:

* The repository does not provide methods to delete or update records on
the blockchain, because records on the blockchain are immutable.
* Unlike other Spring Data modules, custom methods cannot be added to
repositories, as MultiChain supports querying by identifiers only.
* MultiChain does not support transactions.

## 4. License
This sample application and its associated source code in its entirety is being made
available under the Apache License, version 2.0.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
