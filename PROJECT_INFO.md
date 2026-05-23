# Task Management System - Project Information

## Student Information

- Name: Farah M T AbuAssi
- Student ID: 220221408
- Supervisor: Dr. Abdelkareem Alashqar
- Course: Advanced Software Engineering

## Project Overview

This project is a Task Management System implemented using a Microservices Architecture. The system is divided into independently deployable services, where each service owns its business logic and its own private database.

The project follows Domain-Driven Design principles by separating the system into bounded contexts:

- Identity and Access Context
- Task Board Context
- Notification Context

The goal is to achieve loose coupling, independent deployability, information hiding, and database-per-service ownership.

## Main Microservices

### 1. User Service

The User Service represents the Identity and Access Context.

Responsibilities:

- Manage user profile data.
- Store user ID, full name, email, role, and active status.
- Expose REST API to retrieve user information.
- Expose gRPC service so Task Service can validate users before assigning tasks.

Technology:

- Spring Boot
- REST API
- gRPC
- MySQL

Default ports:

- REST: `8081`
- gRPC: `9091`

Database:

- `user_service_db`

Example endpoint:

```http
GET http://localhost:8081/api/users/U100
```

### 2. Task Service

The Task Service represents the main Task Board Context.

Responsibilities:

- Create tasks.
- Retrieve tasks.
- Update task status.
- Store task details, priority, board ID, and assigned user summary.
- Validate assigned users by calling User Service through gRPC before saving a task.
- Publish task update events to RabbitMQ when a task is created or updated.
- Expose REST and GraphQL APIs.

Technology:

- Spring Boot
- REST API
- GraphQL
- gRPC client
- RabbitMQ publisher
- MySQL

Default port:

- REST and GraphQL: `8082`

Database:

- `task_service_db`

Example REST endpoints:

```http
POST http://localhost:8082/api/tasks
GET http://localhost:8082/api/tasks/1
PATCH http://localhost:8082/api/tasks/1/status
```

Example create task body:

```json
{
  "title": "Complete Assignment",
  "description": "Finish microservices implementation",
  "priority": "HIGH",
  "boardId": "B-001",
  "assignedUserId": "U100"
}
```

GraphQL endpoint:

```http
http://localhost:8082/graphql
```

GraphiQL UI:

```http
http://localhost:8082/graphiql
```

### 3. Notification Service

The Notification Service represents the Notification Context.

Responsibilities:

- Listen to task update events from RabbitMQ.
- Consume `TaskStatusChanged` events.
- Store notification records in its own database.
- Expose REST API to retrieve notifications.

Technology:

- Spring Boot
- RabbitMQ consumer
- REST API
- MySQL

Default port:

- REST: `8083`

Database:

- `notification_service_db`

Example endpoint:

```http
GET http://localhost:8083/api/notifications
```

## Additional Services

### API Gateway

The API Gateway provides a single entry point for external clients.

Default port:

- `8080`

It forwards requests to:

- User Service
- Task Service
- Notification Service
- Board Service

### Board Service

The Board Service was added to support asynchronous board summary requests.

Default port:

- `8084`

Database:

- `board_service_db`

Example endpoint:

```http
GET http://localhost:8084/api/board-summaries
```

## Communication Between Services

### Synchronous Communication

Task Service communicates with User Service using gRPC.

Purpose:

- Validate that the assigned user exists.
- Validate that the assigned user is active.
- Prevent saving invalid task assignments.

Flow:

1. Client sends create task request to Task Service.
2. Task Service calls User Service using gRPC.
3. User Service returns user summary.
4. Task Service saves the task only if the user is valid and active.

### Asynchronous Communication

Task Service communicates with Notification Service using RabbitMQ.

Purpose:

- Publish task update events without directly depending on Notification Service.
- Keep services loosely coupled.

RabbitMQ details:

- Exchange: `task.events`
- Routing key: `task.events.updates`
- Notification queue: `task.events.notification-service`

Event type:

```text
TaskStatusChanged
```

## Databases

Each service owns its own MySQL database:

```sql
CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE DATABASE IF NOT EXISTS task_service_db;
CREATE DATABASE IF NOT EXISTS notification_service_db;
CREATE DATABASE IF NOT EXISTS board_service_db;
```

This supports:

- Database per service
- Information hiding
- Data sovereignty
- Independent deployability

## How To Run The Project

### 1. Start MySQL

Start MySQL from XAMPP or any local MySQL server.

Default connection:

```text
localhost:3306
username: root
password: empty
```

### 2. Start RabbitMQ

RabbitMQ must run on:

```text
localhost:5672
```

Default credentials:

```text
username: guest
password: guest
```

### 3. Run User Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\user-service"
mvn spring-boot:run
```

### 4. Run Notification Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\notification-service"
mvn spring-boot:run
```

### 5. Run Task Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\task-service"
mvn spring-boot:run
```

### 6. Optional: Run API Gateway

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\api-gateway"
mvn spring-boot:run
```

### 7. Optional: Run Board Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\board-service"
mvn spring-boot:run
```

## Docker

Task Service includes a Dockerfile.

Build Docker image:

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\task-service"
docker build -t task-service:latest .
```

Run Docker container:

```powershell
docker run --rm -p 8082:8082 task-service:latest
```

If the container needs to call services running on the host machine, use:

```powershell
docker run --rm -p 8082:8082 `
  -e RABBITMQ_HOST=host.docker.internal `
  -e user.service.grpc.host=host.docker.internal `
  task-service:latest
```

## GitHub Monorepo Structure

The project is organized as a monorepo:

```text
task-management-system/
  user-service/
  task-service/
  notification-service/
  api-gateway/
  board-service/
  .github/workflows/ci-cd.yml
  README.md
  PROJECT_INFO.md
```

## CI/CD

The project includes a GitHub Actions workflow:

```text
.github/workflows/ci-cd.yml
```

The workflow:

- Builds the Task Service JAR.
- Builds a Docker image for Task Service.
- Pushes the image to Docker Hub.

Required GitHub repository secrets:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`

## Implemented Requirements

- Three main microservices are implemented: User Service, Task Service, and Notification Service.
- Task Service validates users through gRPC before saving tasks.
- Each service owns an independent MySQL database.
- Notification Service consumes RabbitMQ events from Task Service.
- The project is organized as a monorepo.
- Task Service has a Dockerfile.
- GitHub Actions CI/CD workflow is included.
- GraphQL support is implemented in Task Service.

## Remaining Notes

- Docker Desktop must be running before building or running Docker containers.
- RabbitMQ must be running before testing notifications.
- Docker Hub secrets must be configured in GitHub before the CI/CD workflow can push images.
