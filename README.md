# Credit management service

Simple REST based service which offer Customer and Debt case CRUD operations 

## Description

Application has three main services:
* Customer service
* Debt case service
* User service

Application is based on Java 8.
Project packages are organized based on Hexagonal architecture.

### Customer service
* We can create new customer
* We can update customer information
* We can get customer information
* We can delete customer

Customer object has five mandatory parameters:

1.name

2.surname

3.code (This parameter is added as natural key)

4.country

5.email.

In business requirements there is also parameter 'password', but parameter does not naturally fit in Customer domain
object, therefore this parameter is moved to User domain object, where this parameter belongs more naturally.


### Debt case service
* We can create new debt case
* We can update debt case information
* We can get single debt case information
* We can get all debt cases which belong to concrete customer
* We can delete debt case

Debt case object has four mandatory parameters:

1.number  (This parameter is added as natural key)

2.amount

3.currency

4.dueDate

### User service
* We can create new user
* We can authenticate user
* We can delete user

## Getting Started

### Dependencies

* Java 8
* Intellij idea IDE/ Eclipse IDE

### Executing program

* Pull project from github
* Execute com.intrum.creditmanagementservice.CreditManagementServiceApplication Main method


## Links to resource
Swagger UI: http://localhost:8080/swagger-ui.html
* username -> client
* password -> password

H2 console: http://localhost:8080/h2-console
* username -> sa
* password -> password

## Tests
Project has

1.Unit tests

2.Unit tests with mockito

3.Integration tests only for Web layer using WebMvcTest

4.Integration tests only for repository layer using DataJpaTest

5.Edge to Edge integration tests

Tests can be executed from IDE or via maven command line

Run only unit tests:
```
mvn clean install
```

Run unit tests with integration tests:
```
mvn clean install -P failsafe
```

Test data can be found in path: src/main/resources/data.sql

#Enjoy the code....