# Partner Service
A small Springboot application to perform CRUD operations on Partner Entity

[![Build Status](https://travis-ci.org/aman10choudhary/partner-service.svg?branch=master)](https://travis-ci.org/aman10choudhary/partner-service)
[![Maintainability](https://api.codeclimate.com/v1/badges/bb01596e90a621268916/maintainability)](https://codeclimate.com/github/aman10choudhary/partner-service/maintainability)
[![BCH compliance](https://bettercodehub.com/edge/badge/aman10choudhary/partner-service?branch=master)](https://bettercodehub.com/)

## Inspirations
* Event Driven Architecture
* Microservice Architecture
* Reactive Architecture
* SOLID Design Principle

## Swagger (API Documentation)
[Swagger](https://b2boost-partner-service.herokuapp.com/swagger-ui.html)

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.github.aman10choudhary.partnerservice.PartnerServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
Once the local server has started, you can go to http://localhost:8080/swagger-ui.html to try and test the API specifications
