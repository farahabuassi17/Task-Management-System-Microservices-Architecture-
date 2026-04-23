# 🧩 Task Management System (Microservices Architecture)

## 📌 Project Overview

This project is a **Task Management System** built using a **Microservices Architecture**.
The system is designed to be **scalable, flexible, and maintainable**, by breaking down the application into independently deployable services instead of a monolithic structure.

Each service is aligned with a specific business domain following **Domain-Driven Design (DDD)** principles.

---

## 🏗️ Core Microservices

The system consists of three main services, each with its own database:

### 👤 User Service (Identity & Access Context)

* Manages user data (ID, Name, Email)
* Handles authentication (conceptually)
* Applies **Information Hiding** (no sensitive data exposed)

---

### 📝 Task Service (Task Board Context)

* Manages tasks lifecycle:

  * To-Do
  * In Progress
  * Completed
* Validates task assignee by calling **User Service**

---
## ⚙️ Technologies Used

* **Java 17**
* **Spring Boot**
* **Maven**
* **H2 Database**
---

## 🔗 Communication Between Services

### ✅ Synchronous Communication

* REST API (HTTP + JSON)
* Used between:

  * Task Service ➜ User Service
* Tool: `RestTemplate`

---
## 🧠 Key Architectural Principles

* ✔️ Independent Deployability
* ✔️ Loose Coupling
* ✔️ Database per Service
* ✔️ Information Hiding
* ✔️ Shared DTO Models
* ✔️ Event-Driven Architecture

---

## 📂 Project Structure

```
/task-management-system
│
├── user-service/
├── task-service/
└── README.md
```

---

## 🚀 How to Run the Project

### 1️⃣ Clone the repository

```bash
git clone https://github.com//https://github.com/farahabuassi17/Task-Management-System-Microservices-Architecture-.git
cd TaskManagementSystem
```

---

### 2️⃣ Run User Service

```bash
cd user-service
mvn spring-boot:run
```

Runs on:

```
http://localhost:8082
```

---

### 3️⃣ Run Task Service

```bash
cd task-service
mvn spring-boot:run
```

Runs on:

```
http://localhost:8083
```

---

## 🔍 API Testing

### ✅ Get User (from User Service)

```bash
GET http://localhost:8082/api/users/1
```

---

### ✅ Create Task (from Task Service)

```bash
POST http://localhost:8083/api/tasks
Content-Type: application/json

{
  "title": "Finish Microservices Task",
  "assigneeId": "1"
}
```

---

## 🔄 How It Works

1. Task Service receives task creation request
2. Calls User Service to validate assignee
3. If user exists → task is saved
4. (Future) Notification Service is triggered

---

## 📊 Example Response from User Service

```json
{
  "userId": "1",
  "displayName": "Farah AbuAssi",
  "email": "farah@example.com"
}
```

---

## 👩‍💻 Author

**Name:** Farah M T AbuAssi
**Course:** Advanced Software Engineering (SDEV 4304)
**Supervisor:** Dr. Abdelkareem Alashqar

---

## 🔗 GitHub Repository

👉https://github.com/farahabuassi17/Task-Management-System-Microservices-Architecture-

---

## 🏁 Future Improvements

* Add API Gateway
* Implement Authentication (JWT)
* Add Docker support
* Integrate RabbitMQ fully
* Add Frontend UI

---


