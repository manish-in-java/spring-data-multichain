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
remotely. These features allow using MultiChain blockchains as structured
data stores, with each data stream as a separate data source, and the RPC
server as a remote connectivity solution. As an analogy, a MultiChain
blockchain is the equivalent of a relational database, a data stream is
similar to a relational table in the database, and JSON-RPC is equivalent
to JDBC technology for working with the data in the database.

Key functional areas of this module are a POJO-centric model for interacting
with a MultiChain data stream, and easily writing a repository-style
data-access layer over JSON-RPC.

## Quick Start

### Maven configuration

Add the Maven dependency:

```xml
<dependency>
  <groupId>org.springframework.data</groupId>
  <artifactId>spring-data-multichain</artifactId>
  <version>${version}.RELEASE</version>
</dependency>
```

### MultiChainTemplate

`MultiChainTemplate` is the central support class for MultiChain JSON-RPC
operations. It provides:

* Basic POJO mapping support to and from JSON;
* Convenience methods to interact with the block chain (insert and query
objects. **Note**: A blockchain record is generally immutable, therefore,
updating or deleting a record is not supported);
* Exception translation into Spring's [technology agnostic DAO exception
hierarchy](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/dao.html#dao-exceptions).

### Spring Data repositories

To simplify the creation of data repositories, Spring Data MultiChain provides
a generic repository programming model. It will automatically create a
repository proxy for you that adds implementations of supported methods.

You can have Spring automatically create a proxy for the interface by using
the following JavaConfig:

```java
@Configuration
@EnableMultiChainRepositories
class ApplicationConfig {

  @Override
  public MongoClient mongoClient() throws Exception {
    return new MongoClient();
  }

  @Override
  protected String getDatabaseName() {
    return "springdata";
  }
}
```

This sets up a connection to a local MongoDB instance and enables the detection of Spring Data repositories (through `@EnableMongoRepositories`). The same configuration would look like this in XML:

```xml
<bean id="template" class="org.springframework.data.mongodb.core.MongoTemplate">
  <constructor-arg>
    <bean class="com.mongodb.MongoClient">
       <constructor-arg value="localhost" />
       <constructor-arg value="27017" />
    </bean>
  </constructor-arg>
  <constructor-arg value="database" />
</bean>

<mongo:repositories base-package="com.acme.repository" />
```

This will find the repository interface and register a proxy object in the container. You can use it as shown below:

```java
@Service
public class MyService {

  private final PersonRepository repository;

  @Autowired
  public MyService(PersonRepository repository) {
    this.repository = repository;
  }

  public void doWork() {

     repository.deleteAll();

     Person person = new Person();
     person.setFirstname("Oliver");
     person.setLastname("Gierke");
     person = repository.save(person);

     List<Person> lastNameResults = repository.findByLastname("Gierke");
     List<Person> firstNameResults = repository.findByFirstnameLike("Oli*");
 }
}
```

# License
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
