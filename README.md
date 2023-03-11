# Dziki z Afryki Backend repo na przedmiot Inżynieria Oprogramowania

[**Link to the frontend repository**](https://github.com/mrrys00/dziki-z-afryki-frontend)

## Getting started


### Maven

Maven version >= 3.6

#### Useful commands

* `mvn --version` - Prints out the version of Maven you are running.
* `mvn clean` - Clears the target directory into which Maven normally builds your project.
* `mvn compile` - Compiles the source code of the project. (important when using immutables library, for autocompletion)
* `mvn clean verify` - Cleans the target directory, and runs all integration tests found in the project.
* `mvn clean install` - Clears the target directory and builds the project described by your Maven POM file and installs the resulting artifact (JAR) into your local Maven repository

[More information about Maven](https://jenkov.com/tutorials/maven/maven-commands.html)


### Docker - postgres database

To start Postgres instance in docker container:
```bash
docker run -p 5432:5432 -d \
-e POSTGRES_USER=postgres \
-e POSTGRES_PASSWORD=strong_password \
-e POSTGRES_DB=dziki-z-afryki-db \
-v pgdata:/var/lib/postgresql/data \
--name="postgres-dza-db" \
postgres:15
```

To execute psql commands inside docker container:
```bash
docker exec -it "postgres-dza-db" psql -U postgres "dziki-z-afryki-db"
```
* `\list` - List available databases
* `\connect bd-name` - Switch connection to a new database
* `\dt` - List available tables
* `\x` - Expanded display mode on
* `TABLE example_table;` - run SQL queries
* `exit` - close ssh connection


To stop and remove the container
```bash
docker stop postgres-dza-db
docker rm postgres-dza-db
```


## References

### Docker
* [How to install docker on Ubuntu based systems](https://docs.docker.com/engine/install/ubuntu/)
* [How to run docker commands without SUDO (important for tests)](https://docs.docker.com/engine/install/linux-postinstall/)

### Immutables
* [Immutables (Java annotation processors to generate simple, safe and consistent value objects.)](https://immutables.github.io/)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.9/maven-plugin/reference/html/#build-image)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#web.security)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.9/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Testcontainers](https://www.testcontainers.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

