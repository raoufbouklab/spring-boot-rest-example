# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

### Run the app in docker
```
cd springboot-bookstore

mvn clean install

docker-compose up --build
```

# Connect to database using MySQL Shell :
In the command line, run :
```
docker exec mysql-db mysqld --skip-grant-tables
```

In the MySQL Shell, run the command :
```
\sql

\connect --mysql user@localhost:3306/bookstore

password : password

use bookstore;
describe user;
describe role;
describe user_role;

select * from role;
select * from user;
select * from user_role;
```

# Test the application :

Browse to the app at http://localhost:8082/swagger-ui.html#/

