[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/build-with-spring.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/build-with-spring-boot.svg)](https://forthebadge.com)
![GitHub repo size](https://img.shields.io/github/repo-size/AntoineCanda/Chatop-api?style=for-the-badge)

![GitHub](https://img.shields.io/github/license/AntoineCanda/Chatop-api?style=flat-square)

# Châtop, API of Backend server

This is the server part of the Châtop project.
It is a RESTful API used to store and retrieve data from the ChatopAPI database protected by a Bearer token authentication system.

The front part is available here : [Estate-Front](https://github.com/AntoineCanda/Developpez-le-back-end-en-utilisant-Java-et-Spring) forked from OpenClassRooms projects.

## Prerequisites

What things you need to install the software and how to install them

You will need for this project :

- [maven](https://maven.apache.org/install.html) to build the project (current : 3.6.3)
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) to run the project (current : openjdk 17.0.6 2023-01-17)
- [Mysql](https://dev.mysql.com/downloads/mysql/) to run the database

### Getting Started

You need to follow the following instructions (bash needed) for getting a copy of the project up and running on your local machine.

- Get the git repository with the following command :

    ```bash
    git clone https://github.com/AntoineCanda/Chatop-api.git
    ```

- Go to the project folder

    ```bash
    cd Chatop-api
    ```

- Create a .env file:

    ```bash
    touch .env
    ```

- Add the following environment variables:

    ```text
    MYSQLDB_USER=<Set your db user>
    MYSQLDB_PASSWORD=<Set your db user password>
    MYSQLDB_URL=jdbc:mysql:<Set the host and port of your database>/ChatopAPI (You can also change the database name by editing the ChatopAPI value by another one)
    SERVER_PORT=<Use the port you want to use for the server>
    JWT_KEY=<Put your secret key used for create more secure token. Create a word with uppercase and lowercase caracters, number and special caracter with a length of 64 for having a more secure one.>
    ```

- Create the database and the table needed for the application. I use for the example the database name ChatopAPI.

    ```mysql
        CREATE DATABASE ChatopAPI;
        USE ChatopAPI;
    ```

- You can create the tables and index with the following command:

    ```mysql
        source resources/sql/schemaDatabase.sql;
    ```

- Run the following command to install the project

    ```bash
        mvn clean install
    ```

- Run the following command to run the project

    ```bash
    mvn spring-boot:run
    ```

- The project is now running on  [http://localhost:9001](http://localhost:9001)
- The documentation is available on [http://localhost:9001/swagger-ui/index.html](http://localhost:9001/swagger-ui/index.html)


#### Environment variables

- The environment variables are defined in the .env file.
- The variables for spring are defined in the ./src/main/resources/application.properties file.


## Getting started with the API

The documentation is based on the [spring doc](https://springdoc.org/#getting-started) specification.


***A specific endpoint is defined in configuration to get the documentation of the API out of the security scope.
This is done only for demonstration purpose, in a real world application, the documentation should be secured.***

The documentation path is defined per se in spring-doc specification of
the [swagger-ui](https://springdoc.org/v2/#getting-started)
endpoint.

For example, if you run on your machine without changing the server port, the documentation will be available
here: <http://localhost:9001/swagger-ui/index.html>


### About the security

The security is based on [spring security](https://spring.io/projects/spring-security) and [JWT](https://jwt.io/).

The security is configured in the SecurityConfiguration class.
The security is configured to allow access to the documentation endpoints and the authentication endpoint.
To access to the other chatop api endpoints, you will need to be an authenticated users. This is verified at each request with JWT token given in the Authorization header.


## Built With

- [Maven](https://maven.apache.org/) - Dependency Management
- [Spring](https://spring.io/) - The web framework used
- [Spring Security](https://spring.io/projects/spring-security) - Authentication and authorization framework
- [Spring Data](https://spring.io/projects/spring-data) - Data access framework
- [Spring Doc](https://springdoc.org/) - OpenAPI 3 and Swagger 2 generation for Spring REST APIs
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new
  Spring Applications
