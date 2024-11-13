
# SpringBootTemplate

This repository provides a template for a Spring Boot application. It includes essential configurations and dependencies to get started with a Spring Boot project quickly.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
  - [Clone the Repository](#clone-the-repository)
  - [Build the Project](#build-the-project)
  - [Run the Application](#run-the-application)
- [Configuration](#configuration)
  - [Database Setup](#database-setup)
  - [Docker Setup](#docker-setup)
- [GitHub Actions](#github-actions)
  - [Setup Workflow](#setup-workflow)
- [Endpoints](#endpoints)
- [License](#license)

## Features

- Spring Boot framework for rapid development
- REST API template
- Database integration with PostgreSQL
- Docker and Docker Compose configuration for containerization
- Modular structure for easy extension

## Requirements

- [Java 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi) (or use `mvnw` included in the repository)
- [Docker](https://www.docker.com/get-started)
- [PostgreSQL](https://www.postgresql.org/) (optional, for local development)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/ArdiJorganxhi/SpringBootTemplate.git
cd SpringBootTemplate
```

### Build the Project

```bash
./mvnw clean install
```

### Run the Application

To run the application locally:

```bash
./mvnw spring-boot:run
```

## Configuration

### Database Setup

The application is pre-configured to use PostgreSQL. Update the following properties in `application.properties` to connect to your database:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Docker Setup

This template includes a Docker Compose file that will:

1. Start a PostgreSQL container (or connect to an existing one if available).
2. Start the Spring Boot application.

Run the application in Docker:

```bash
docker-compose up
```


### Setup Workflow

This repository contains a GitHub Actions workflow that will run Docker Compose to start PostgreSQL instance, build project from Dockerfile and run it.
In order to run it, you need to setup GitHub Secrets at your repository for these variables:

- POSTGRES_USERNAME
- POSTGRES_PASSWORD
- POSTGRES_DB
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USER
- SPRING_DATASOURCE_PASSWORD
- JWT_SECRET

Also, you need to setup your GitHub Actions Self Hosted Runner to run at your own server:

[Setup GitHub Actions Self-Hosted Runner](https://docs.github.com/en/actions/hosting-your-own-runners/managing-self-hosted-runners/adding-self-hosted-runners)





## Endpoints

- `GET /api/v1/resource`: Example endpoint to retrieve resources.
- `POST /api/v1/resource`: Example endpoint to create a new resource.
- Additional endpoints can be added based on your project’s requirements.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
