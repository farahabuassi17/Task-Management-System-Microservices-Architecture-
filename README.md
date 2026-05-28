# Task Management System - Microservices Architecture

## Project Overview

This project is a **Task Management System** implemented using a **Microservices Architecture** with Spring Boot. The system is divided into independent services, where each service owns its business logic and its own database.

The project demonstrates:

- Independent deployability
- Loose coupling
- Database per service
- Information hiding
- Synchronous communication using gRPC
- Asynchronous communication using RabbitMQ
- REST and GraphQL APIs
- Docker containerization
- GitHub Actions CI/CD

## Main Microservices

The required application includes three main microservices.

### User Service

The `user-service` represents the Identity and Access Context.

Responsibilities:

- Manage user profile data.
- Store user ID, full name, email, role, and active status.
- Expose REST API for user lookup.
- Expose gRPC API so `task-service` can validate assigned users.

Default ports:

- REST: `8081`
- gRPC: `9091`

Database:

- `user_service_db`

Example endpoint:

```http
GET http://localhost:8081/api/users/U100
```

### Task Service

The `task-service` represents the Task Management Context.

Responsibilities:

- Create tasks.
- Retrieve tasks.
- Update task status.
- Store task title, description, status, priority, board ID, and assigned user summary.
- Validate assigned users through `user-service` using gRPC before saving a task.
- Publish task update events to RabbitMQ.
- Expose REST and GraphQL APIs.

Default port:

- REST and GraphQL: `8082`

Database:

- `task_service_db`

Example endpoints:

```http
POST http://localhost:8082/api/tasks
GET http://localhost:8082/api/tasks/1
PATCH http://localhost:8082/api/tasks/1/status
POST http://localhost:8082/graphql
GET http://localhost:8082/graphiql
```

Important GraphQL note:

- `/graphql` is the JSON API endpoint.
- `/graphiql` is the browser UI and returns HTML.

### Notification Service

The `notification-service` represents the Notification Context.

Responsibilities:

- Listen to RabbitMQ task update events.
- Consume `TaskStatusChanged` events.
- Store notification records in its own database.
- Expose REST API for retrieving notifications.

Default port:

- REST: `8083`

Database:

- `notification_service_db`

Example endpoint:

```http
GET http://localhost:8083/api/notifications
```

## Supporting Services

### API Gateway

The `api-gateway` provides a single entry point for external clients.

Default port:

- `8080`

It forwards requests to:

- `user-service`
- `task-service`
- `notification-service`
- `board-service`

### Board Service

The `board-service` is an additional supporting service for board summary requests. It is not one of the three required main microservices, but it supports the board-related functionality mentioned in the project design.

Default port:

- `8084`

Database:

- `board_service_db`

Example endpoint:

```http
GET http://localhost:8084/api/board-summaries
```

## Technologies Used

- Java 17
- Spring Boot
- Maven
- MySQL
- gRPC
- GraphQL
- RabbitMQ
- Docker
- Docker Hub
- GitHub Actions

## Communication Between Services

### Synchronous Communication

`task-service` communicates with `user-service` using gRPC.

Purpose:

- Validate that the assigned user exists.
- Validate that the assigned user is active.
- Prevent invalid task assignments.

Flow:

1. A client sends a create task request to `task-service`.
2. `task-service` calls `user-service` using gRPC.
3. `user-service` returns user information.
4. `task-service` saves the task only if the user is valid and active.

### Asynchronous Communication

RabbitMQ is used for asynchronous service communication.

Task notification event flow:

```text
task-service -> RabbitMQ -> notification-service
```

RabbitMQ details:

```text
Exchange: task.events
Routing key: task.events.updates
Queue: task.events.notification-service
Event type: TaskStatusChanged
```

Board summary request flow:

```text
task-service -> RabbitMQ -> board-service
```

RabbitMQ details:

```text
Exchange: board.summary
Routing key: board.summary.requests
Queue: board.summary.board-service
```

## Project Structure

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

## Databases

Each service owns its own MySQL database.

```sql
CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE DATABASE IF NOT EXISTS task_service_db;
CREATE DATABASE IF NOT EXISTS notification_service_db;
CREATE DATABASE IF NOT EXISTS board_service_db;
```

This supports:

- Database per service
- Data sovereignty
- Service autonomy
- Independent schema evolution

## How to Run the Project

### 1. Start MySQL

Start MySQL from XAMPP or another local MySQL server.

Default connection:

```text
Host: localhost
Port: 3306
Username: root
Password: empty
```

Create the databases:

```powershell
& "C:\xampp\mysql\bin\mysql.exe" -u root -e "CREATE DATABASE IF NOT EXISTS user_service_db; CREATE DATABASE IF NOT EXISTS task_service_db; CREATE DATABASE IF NOT EXISTS notification_service_db; CREATE DATABASE IF NOT EXISTS board_service_db;"
```

### 2. Start RabbitMQ

RabbitMQ is required when testing notifications and board summary messaging.

Default connection:

```text
Host: localhost
Port: 5672
Username: guest
Password: guest
```

If RabbitMQ is not running, task creation still works, but notification events will not be consumed.

### 3. Run User Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\user-service"
.\mvnw.cmd spring-boot:run
```

Runs on:

```text
REST: http://localhost:8081
gRPC: localhost:9091
```

### 4. Run Notification Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\notification-service"
.\mvnw.cmd spring-boot:run
```

Runs on:

```text
REST: http://localhost:8083
```

### 5. Run Task Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\task-service"
.\mvnw.cmd spring-boot:run
```

Runs on:

```text
REST: http://localhost:8082
GraphQL: http://localhost:8082/graphql
GraphiQL: http://localhost:8082/graphiql
```

### 6. Optional: Run API Gateway

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\api-gateway"
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8080
```

### 7. Optional: Run Board Service

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\board-service"
mvn spring-boot:run
```

Runs on:

```text
http://localhost:8084
```

### Maven Note

If `mvn` is not recognized in PowerShell, use the Maven wrapper where available:

```powershell
.\mvnw.cmd spring-boot:run
```

## API Testing Examples

### Get User

```http
GET http://localhost:8081/api/users/U100
```

Example response:

```json
{
  "id": "U100",
  "fullName": "Farah AbuAssi",
  "email": "farah@example.com",
  "role": "PROJECT_MANAGER",
  "active": true
}
```

### Create Task

```http
POST http://localhost:8082/api/tasks
Content-Type: application/json
```

```json
{
  "title": "Docker Test Task",
  "description": "Task created from Docker container",
  "priority": "HIGH",
  "boardId": "B-001",
  "assignedUserId": "U100"
}
```

### Create Invalid Task

Use a non-existing user to test gRPC validation failure.

```json
{
  "title": "Invalid User Task",
  "description": "This should fail because user does not exist",
  "priority": "LOW",
  "boardId": "B-001",
  "assignedUserId": "U999"
}
```

### Update Task Status

```http
PATCH http://localhost:8082/api/tasks/1/status
Content-Type: application/json
```

```json
{
  "status": "IN_PROGRESS"
}
```

### Get Notifications

```http
GET http://localhost:8083/api/notifications
```

### GraphQL Query

Send this request to `/graphql`, not `/graphiql`.

```http
POST http://localhost:8082/graphql
Content-Type: application/json
```

```json
{
  "query": "query { task(id: 1) { id title status priority boardId assignedUserName assignedUserEmail } }"
}
```

### GraphQL Mutation

```json
{
  "query": "mutation { createTask(input: { title: \"GraphQL Task\", description: \"Created from GraphQL\", priority: \"HIGH\", boardId: \"B-001\", assignedUserId: \"U100\" }) { id title status priority boardId assignedUserName assignedUserEmail } }"
}
```

## Docker

`task-service` is containerized using Docker.

Dockerfile location:

```text
task-service/Dockerfile
```

Build the image locally:

```powershell
cd "C:\Users\hp\OneDrive\Desktop\task management springboot\task management springboot\task-service"
docker build -t task-service:latest .
```

Run the local image:

```powershell
docker run --name task-service-container --rm -p 8082:8082 task-service:latest
```

Run with host MySQL, RabbitMQ, and User Service:

```powershell
docker run --name task-service-container --rm -p 8082:8082 `
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/task_service_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" `
  -e MYSQL_USER=root `
  -e MYSQL_PASSWORD= `
  -e RABBITMQ_HOST=host.docker.internal `
  -e USER_SERVICE_GRPC_HOST=host.docker.internal `
  -e USER_SERVICE_GRPC_PORT=9091 `
  task-service:latest
```

Docker Hub image:

```text
farahabuassi/task-service:latest
```

Pull and run from Docker Hub:

```powershell
docker pull farahabuassi/task-service:latest
docker run --name task-service-container --rm -p 8082:8082 farahabuassi/task-service:latest
```

When running the Docker Hub image with local MySQL and local `user-service`, use the same environment variables shown in the previous command.

## GitHub Monorepo

The complete source code is stored in one public GitHub repository.

Repository:

[Task-Management-System-Microservices-Architecture-](https://github.com/farahabuassi17/Task-Management-System-Microservices-Architecture-)

The monorepo contains a separate folder for each service:

- `user-service`
- `task-service`
- `notification-service`
- `api-gateway`
- `board-service`

## GitHub Actions CI/CD

CI/CD is implemented for `task-service` using GitHub Actions and Docker Hub.

Workflow file:

```text
.github/workflows/ci-cd.yml
```

Workflow name:

```text
Task Service CI/CD
```

Pipeline steps:

1. Checkout repository.
2. Set up Java 17.
3. Build the Task Service JAR.
4. Set up Docker Buildx.
5. Log in to Docker Hub.
6. Build the Docker image.
7. Push the image to Docker Hub.

Required GitHub repository secrets:

```text
DOCKERHUB_USERNAME
DOCKERHUB_TOKEN
```

CI/CD result:

- `Build Task Service`: Success
- `Build and Push Docker Image`: Success
- Docker Hub image pushed as `farahabuassi/task-service:latest`

## Seeded Users for Testing

The `user-service` includes seeded users for manual testing.

```text
U100 -> active
U200 -> active
U300 -> inactive
```

Use:

- `U100` or `U200` for successful task creation.
- `U300` to test inactive user validation.
- `U999` to test non-existing user validation.

## Implemented Requirements

| Requirement | Status |
|---|---|
| Complete User Service, Task Service, and Notification Service | Completed |
| Task Service communicates with User Service using gRPC | Completed |
| Each service owns an independent MySQL database | Completed |
| Public GitHub monorepo | Completed |
| Docker containerization for Task Service | Completed |
| CI/CD with GitHub Actions and Docker Hub | Completed |

## Author

- **Name:** Farah M T AbuAssi
- **Student ID:** 220221408
- **Course:** Advanced Software Engineering
- **Supervisor:** Dr. Abdelkareem Alashqar
