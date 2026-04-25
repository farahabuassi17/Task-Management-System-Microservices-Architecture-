# Task Management Spring Boot Microservices

This workspace contains the first implementation phase for the Chapter 5 running project using two collaborating Spring Boot microservices:

- `user-service`: owns user data and exposes user lookup APIs.
- `task-service`: owns task data and calls `user-service` before creating a task.

## Implemented communication style

- Style: synchronous request-response
- Technology: REST over HTTP
- Serialization: JSON
- Databases: separate MySQL databases per service

## Services and ports

- `user-service`: `http://localhost:8081`
- `task-service`: `http://localhost:8082`

## Exposed APIs

### User Service

- `GET /api/users/{id}`

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

### Task Service

- `POST /api/tasks`
- `GET /api/tasks/{id}`

Example create request:

```json
{
  "title": "Prepare chapter 5 submission",
  "description": "Document the running project and verify the services.",
  "assignedUserId": "U100"
}
```

Example create response:

```json
{
  "id": 1,
  "title": "Prepare chapter 5 submission",
  "description": "Document the running project and verify the services.",
  "status": "OPEN",
  "assignedUserId": "U100",
  "assignedUserName": "Farah AbuAssi",
  "assignedUserEmail": "farah@example.com",
  "createdAt": "2026-04-25T10:00:00Z"
}
```

## Collaboration flow

1. A client sends `POST /api/tasks` to `task-service`.
2. `task-service` calls `GET /api/users/{id}` on `user-service`.
3. If the user exists and is active, the task is stored in the task database.
4. If the user is missing or inactive, `task-service` returns an error and rejects task creation.

## Verified run steps

The project was previously verified in the workspace, and it is now configured to use MySQL instead of H2.

Database names:

- `user-service` -> `user_service_db`
- `task-service` -> `task_service_db`

Before running the services, make sure MySQL is running on `localhost:3306`.

Optional SQL:

```sql
CREATE DATABASE IF NOT EXISTS user_service_db;
CREATE DATABASE IF NOT EXISTS task_service_db;
```

The services currently use:

```powershell
.\apache-maven-3.9.14\bin\mvn.cmd "-Dmaven.user.home=C:\Users\Leenah\OneDrive\Desktop\task management springboot\.m2home" "-Dmaven.repo.local=C:\Users\Leenah\OneDrive\Desktop\task management springboot\.m2repo" test
```

To build runnable jars:

```powershell
.\apache-maven-3.9.14\bin\mvn.cmd "-Dmaven.user.home=C:\Users\Leenah\OneDrive\Desktop\task management springboot\.m2home" "-Dmaven.repo.local=C:\Users\Leenah\OneDrive\Desktop\task management springboot\.m2repo" package -DskipTests
```

To run the services in two terminals:

```powershell
cd "C:\Users\Leenah\OneDrive\Desktop\task management springboot\user-service"
java -jar target\user-service-0.0.1-SNAPSHOT.jar
```

```powershell
cd "C:\Users\Leenah\OneDrive\Desktop\task management springboot\task-service"
java -jar target\task-service-0.0.1-SNAPSHOT.jar
```

If your MySQL username or password is not the default, run with environment variables:

```powershell
$env:MYSQL_USER="root"
$env:MYSQL_PASSWORD="your_password"
```

Then verify the collaboration:

```powershell
Invoke-RestMethod -Method Get -Uri "http://localhost:8081/api/users/U100"
Invoke-RestMethod -Method Post -Uri "http://localhost:8082/api/tasks" -ContentType "application/json" -Body '{"title":"Prepare chapter 5 submission","description":"Verify collaboration between services","assignedUserId":"U100"}'
Invoke-RestMethod -Method Get -Uri "http://localhost:8082/api/tasks/1"
```

## Notes

- This implementation satisfies the minimum first phase requested in the PDF: two collaborated microservices using Spring Boot.
- The project now depends on MySQL, not H2.
- Message broker, GraphQL, and gRPC are not implemented in this phase.
- Add your public GitHub repository link to the final submission document after pushing this project.
