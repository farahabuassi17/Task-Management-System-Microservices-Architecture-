# 🧩 Task Management System (Microservices Architecture)

## 📌 Project Overview

This project is a **Task Management System** built using a **Microservices Architecture**.  
The system is designed to be scalable, flexible, and maintainable by separating the application into independently deployable services rather than a monolithic structure.

Each service is aligned with a specific business responsibility and maintains its own data store.

## 🏗️ Core Microservices

The current system consists of two main services, each with its own database:

### 👤 User Service (Identity Context)

- Manages user data such as ID, full name, email, and role
- Exposes user lookup functionality
- Keeps user ownership isolated inside the service
- Provides user validation for other services through `gRPC`

### 📝 Task Service (Task Management Context)

- Manages task creation and retrieval
- Stores task details and assignment metadata
- Validates the assigned user by calling `user-service`
- Exposes both REST and GraphQL interfaces

## ⚙️ Technologies Used

- Java 17
- Spring Boot
- Maven
- MySQL
- gRPC
- GraphQL

## 🔗 Communication Between Services

### ✅ Synchronous Communication

The project currently uses synchronous communication in two forms:

- **gRPC** between:
  - `task-service` ➜ `user-service`
- **REST API (HTTP + JSON)** for external service access
- **GraphQL** exposed by `task-service` for queries and mutations

## 🧠 Key Architectural Principles

- ✔️ Independent Deployability
- ✔️ Loose Coupling
- ✔️ Database per Service
- ✔️ Information Hiding
- ✔️ Clear Service Boundaries
- ✔️ Bonus Integration with gRPC and GraphQL

## 📂 Project Structure

```text
/task-management-system
│
├── user-service/
├── task-service/
└── README.md
```

## 🚀 How to Run the Project

### 1️⃣ Ensure MySQL is running

The application expects MySQL on:

- `localhost:3306`

Databases used:

- `user_service_db`
- `task_service_db`

If needed:

```sql
CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE DATABASE IF NOT EXISTS task_service_db;
```

### 2️⃣ Run User Service

```powershell
cd user-service
mvn spring-boot:run
```

Runs on:

- REST: `http://localhost:8081`
- gRPC: `localhost:9091`

### 3️⃣ Run Task Service

```powershell
cd task-service
mvn spring-boot:run
```

Runs on:

- REST: `http://localhost:8082`
- GraphQL: `http://localhost:8082/graphql`

## 🔍 API Testing

### ✅ Get User (from User Service)

```http
GET http://localhost:8081/api/users/U100
```

### ✅ Create Task (from Task Service REST API)

```http
POST http://localhost:8082/api/tasks
Content-Type: application/json
```

```json
{
  "title": "Finish Microservices Task",
  "description": "Verify gRPC collaboration",
  "assignedUserId": "U100"
}
```

### ✅ Create Task (from Task Service GraphQL API)

```http
POST http://localhost:8082/graphql
Content-Type: application/json
```

```json
{
  "query": "mutation { createTask(input: { title: \"GraphQL Task\", description: \"Created from GraphQL\", assignedUserId: \"U100\" }) { id title status assignedUserId assignedUserName assignedUserEmail } }"
}
```

### ✅ Get Task (from GraphQL)

```json
{
  "query": "query { task(id: 1) { id title description status assignedUserId assignedUserName assignedUserEmail } }"
}
```

## 🔄 How It Works

1. A client sends a task creation request to `task-service`.
2. `task-service` calls `user-service` using `gRPC` to validate the assigned user.
3. If the user exists and is active, the task is saved.
4. The task can later be retrieved through REST or GraphQL.

## 📊 Example Response from User Service

```json
{
  "id": "U100",
  "fullName": "Farah AbuAssi",
  "email": "farah@example.com",
  "role": "PROJECT_MANAGER",
  "active": true
}
```

## 📊 Example Response from Task Service

```json
{
  "id": 1,
  "title": "Finish Microservices Task",
  "description": "Verify gRPC collaboration",
  "status": "OPEN",
  "assignedUserId": "U100",
  "assignedUserName": "Farah AbuAssi",
  "assignedUserEmail": "farah@example.com",
  "createdAt": "2026-04-25T13:01:56.776104300Z"
}
```

## 🧪 Verification Notes

The seeded users currently available in `user-service` are:

- `U100` → active
- `U200` → active
- `U300` → inactive

For successful testing, use:

- `U100` or `U200`

To test validation failures, use:

- `U300` for inactive user
- any non-existing ID such as `U999`

## 👩‍💻 Author

- **Name:** Farah M T AbuAssi
- **Course:** Advanced Software Engineering (SDEV 4304)
- **Supervisor:** Dr. Abdelkareem Alashqar

## 🔗 GitHub Repository

👉 [Task-Management-System-Microservices-Architecture-](https://github.com/farahabuassi17/Task-Management-System-Microservices-Architecture-)

## 🏁 Future Improvements

- Implement Authentication (JWT)
- Add Docker support
- Add frontend UI

## Added From The Report Requirements

The code now includes the missing implementation pieces described in the PDF:

- `api-gateway`: runs on `http://localhost:8080` and exposes one entry point for users, tasks, task status updates, and notifications.
- `notification-service`: runs on `http://localhost:8083`, owns `notification_service_db`, and stores notifications consumed from task events.
- `board-service`: runs on `http://localhost:8084`, owns `board_service_db`, and consumes asynchronous board-summary requests.
- RabbitMQ event communication: `task-service` publishes `TaskStatusChanged` events to exchange `task.events` with routing key `task.events.updates`.
- Board async request flow: `task-service` publishes board summary requests to exchange `board.summary` with routing key `board.summary.requests`.
- Task update lifecycle: `PATCH /api/tasks/{id}/status` updates task status and publishes a notification event.
- Expanded task schema: tasks now include `priority` and `boardId`, matching the detailed task API schema in the report.
- Expanded GraphQL schema: `createTask` accepts `priority` and `boardId`, and `updateTaskStatus` was added as a mutation.

### Additional Databases

```sql
CREATE DATABASE IF NOT EXISTS notification_service_db;
CREATE DATABASE IF NOT EXISTS board_service_db;
```

### Additional Run Commands

Run RabbitMQ before testing asynchronous notifications, then start:

```powershell
cd api-gateway
mvn spring-boot:run
```

```powershell
cd notification-service
mvn spring-boot:run
```

```powershell
cd board-service
mvn spring-boot:run
```

### New API Examples

```http
PATCH http://localhost:8082/api/tasks/1/status
Content-Type: application/json
```

```json
{
  "status": "IN_PROGRESS"
}
```

```http
GET http://localhost:8083/api/notifications
```

```http
POST http://localhost:8082/api/boards/B-001/summary-requests
Content-Type: application/json
```

```json
{
  "requestedBy": "U100"
}
```

```http
GET http://localhost:8084/api/board-summaries
```

## Docker

`task-service` includes a Dockerfile, so it can be built and run as a container:

```powershell
cd task-service
docker build -t task-service .
docker run --name task-service --rm -p 8082:8082 task-service
```

When running the container against local MySQL, RabbitMQ, and `user-service`, configure the service URLs/hosts using environment variables if needed:

```powershell
docker run --name task-service --rm -p 8082:8082 `
  -e MYSQL_USER=root `
  -e MYSQL_PASSWORD= `
  -e RABBITMQ_HOST=host.docker.internal `
  -e user.service.grpc.host=host.docker.internal `
  task-service
```

## GitHub Actions CI/CD

The workflow file `.github/workflows/ci-cd.yml` builds the `task-service` JAR, builds a Docker image, and pushes it to Docker Hub.

Before using the push step, add these repository secrets on GitHub:

- `DOCKERHUB_USERNAME`
- `DOCKERHUB_TOKEN`
