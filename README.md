# Châtop - Backend API

[![forthebadge](https://forthebadge.com/images/badges/uses-git.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/build-with-spring.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/build-with-spring-boot.svg)](https://forthebadge.com)
![GitHub repo size](https://img.shields.io/github/repo-size/AntoineCanda/Chatop-api?style=for-the-badge)
![GitHub](https://img.shields.io/github/license/AntoineCanda/Chatop-api?style=flat-square)

Châtop is a RESTful API for managing property rentals, providing a backend server that stores and retrieves data from the ChatopAPI database, protected by a Bearer token authentication system.

The frontend part is available here: [Châtop Frontend](https://github.com/AntoineCanda/Developpez-le-back-end-en-utilisant-Java-et-Spring), forked from OpenClassRooms projects.

## Table of Contents

- [Châtop - Backend API](#châtop---backend-api)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites](#prerequisites)
  - [Getting Started](#getting-started)
  - [Environment Variables](#environment-variables)
  - [Getting Started with the API](#getting-started-with-the-api)
  - [Models, Repositories, and DTOs](#models-repositories-and-dtos)
  - [Controllers](#controllers)
  - [Services](#services)
  - [Exceptions](#exceptions)
  - [About the Security](#about-the-security)
  - [API Endpoints and Features](#api-endpoints-and-features)
  - [Contributing](#contributing)
  - [Deployment](#deployment)
  - [Built With](#built-with)

## Prerequisites

You will need the following software to run this project:

- [Maven](https://maven.apache.org/install.html) to build the project (current: 3.6.3)
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) to run the project (current: openjdk 17.0.6 2023-01-17)
- [MySQL](https://dev.mysql.com/downloads/mysql/) to run the database

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

1. Clone the repository:

    ```bash
    git clone https://github.com/AntoineCanda/Chatop-api.git
    cd Chatop-api
    ```

2. Create a `.env` file:

    ```bash
    touch .env
    ```

3. Add the following environment variables to the `.env` file:

    ```env
    MYSQLDB_USER=<Set your db user>
    MYSQLDB_PASSWORD=<Set your db user password>
    MYSQLDB_URL=jdbc:mysql://<Set the host and port of your database>/ChatopAPI
    SERVER_PORT=<Set the port for the server>
    JWT_KEY=<Set your JWT secret key>
    ```

4. Create the database and tables:

    ```mysql
    CREATE DATABASE ChatopAPI;
    USE ChatopAPI;
    SOURCE resources/sql/schemaDatabase.sql;
    ```

5. Install project dependencies:

    ```bash
    mvn clean install
    ```

6. Run the project:

    ```bash
    mvn spring-boot:run
    ```

7. The project will be running at [http://localhost:9001](http://localhost:9001). API documentation is available at [http://localhost:9001/swagger-ui/index.html](http://localhost:9001/swagger-ui/index.html).

## Environment Variables

- Environment variables are defined in the `.env` file.
- Spring variables are defined in the `src/main/resources/application.properties` file.

## Getting Started with the API

The API documentation is based on the [SpringDoc](https://springdoc.org/#getting-started) specification.

**Note:** The API documentation endpoint is exposed without security for demonstration purposes. In a real-world application, the documentation should be secured.

Access the API documentation at [http://localhost:9001/swagger-ui/index.html](http://localhost:9001/swagger-ui/index.html).

## Models, Repositories, and DTOs

- **Models:** The entities are located in the `models` package. They represent the database models such as `Rental`, `User`, `Message`, and `Token`. There are also two enumerations used in the `Rental` and `Token` models (`TokenType` and `Role`).

- **Repositories:** The `repositories` package contains all the repository classes, one for each table in the database (`Message`, `Rental`, `User`, and `Token`).

- **DTOs:** The `dto` package contains Data Transfer Object (DTO) models used throughout the application.

## Controllers

The `controllers` package contains all the controller classes, which define the application's endpoints.

## Services

The `services` package contains the service classes used in the application. Each controller has a corresponding service class, and there is a dedicated service for token generation used in the application's security.

## Exceptions

The `exceptions` package contains custom exception classes. These exceptions follow a pattern that includes:
- An HTTP status
- A custom API error code
- A list of error messages

This structure provides detailed information for developers to debug the application effectively. An exception handler class in this package provides handlers for both custom exceptions and all other exceptions.

## About the Security

Security is implemented using [Spring Security](https://spring.io/projects/spring-security) and [JWT](https://jwt.io/).

The security configuration is located in the `SecurityConfiguration` class. It allows access to the documentation and authentication endpoints. All other endpoints require authentication via a JWT token in the Authorization header.

## API Endpoints and Features

- **POST** `/api/auth/register` - Register a new user
- **POST** `/api/auth/login` - User login
- **GET** `/api/auth/me` - Get user information
- **POST** `/api/messages` - Send a message about a rental
- **GET** `/api/rentals` - Get the list of all rentals
- **GET** `/api/rentals/{id}` - Get information about a rental
- **POST** `/api/rentals` - Register a new rental
- **PUT** `/api/rentals/{id}` - Update rental information

## Contributing

To contribute to this project, follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit them (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Create a new Pull Request.

## Deployment

Instructions for deploying the application to a production environment:

1. Ensure all environment variables are set correctly in the production environment.
2. Build the project using Maven:

    ```bash
    mvn clean package
    ```

3. Deploy the generated `.jar` file to your server.
4. Start the application:

    ```bash
    java -jar target/Chatop-api-0.0.1-SNAPSHOT.jar
    ```

## Built With

- [Maven](https://maven.apache.org/) - Dependency management
- [Spring Boot](https://spring.io/projects/spring-boot) - Framework for building the application
- [Spring Security](https://spring.io/projects/spring-security) - Authentication and authorization framework
- [Spring Data](https://spring.io/projects/spring-data) - Data access framework
- [SpringDoc](https://springdoc.org/) - OpenAPI 3 and Swagger 2 generation for Spring REST APIs
- [Lombok](https://projectlombok.org/) - For generating getters, setters, and constructors
