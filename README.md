# Test Case Management Service

## Overview
This project is a **Test Case Management Service** built using **Java 8+, Spring Boot, and MongoDB**. It provides REST APIs to manage test cases, including creating, retrieving, updating, and deleting test cases. The service ensures clean architecture, follows design patterns, and applies best practices for scalability and maintainability.

## Features
- **CRUD Operations**: Create, Read, Update, and Delete test cases.
- **Pagination & Filtering**: Retrieve test cases with pagination and query filtering.
- **Validation & Exception Handling**: Ensures valid inputs and provides meaningful error messages.
- **MongoDB with Indexing**: Stores test cases efficiently with optimized queries.
- **Unit & Integration Testing**: JUnit and Mockito for robust testing.
- **Swagger API Documentation**: Simplifies API exploration and testing.
- **Spring Boot Profiling**: Supports different environment configurations.

## API Endpoints
| Method | Endpoint                 | Description |
|--------|--------------------------|-------------|
| GET | `/api/v1/testcases`      | Retrieve all test cases (supports pagination & filtering) |
| GET | `/api/v1/testcases/{id}` | Retrieve a test case by ID |
| POST | `/api/v1/testcases`      | Create a new test case |
| PUT | `/api/v1/testcases/{id}` | Update an existing test case |
| DELETE | `/api/v1/testcases/{id}` | Delete a test case |

## Data Model
Each test case contains:
- **title** (*String, required*)
- **description** (*String, optional*)
- **status** (*optional, Enum: Pending, In Progress, Passed, Failed*)
- **priority** (*optional, Enum: Low, Medium, High*)
- **createdAt** (*Date, auto-generated*)
- **updatedAt** (*Date, auto-updated*)

## Technology Stack
- **Java 8+**
- **Spring Boot**
- **MongoDB (Spring Data MongoDB)**
- **Maven (Build Tool)**
- **JUnit 5 & Mockito (Testing)**
- **Swagger/OpenAPI (API Documentation)**
- **Git (Version Control) - Github**

## Setup & Installation
### Prerequisites
- Install **Java 8+**
- Install **Maven**
- Install **MongoDB** and ensure it's running

### Steps to Run Locally
```bash
# Clone the repository
git clone https://github.com/antariksha-git/TestCaseManagement.git
cd TestCaseManagement

# Build the project
mvn clean install

# Run the service
mvn spring-boot:run
```
The service will be available at `http://localhost:8080`.

## Configuration (Spring Boot Profiles)
You may not have to update `application.yml` to specify the active profile as by default it is set to dev
```yaml
spring:
  profiles:
    active: dev  # By default, no need to change
```

## Running Tests
To execute unit and integration tests:
```bash
mvn test
```

## API Documentation (Swagger)
After starting the service, access Swagger UI at:
```
http://localhost:8080/docs.html
```



