# ProjectMonitor - SIH-style Project Monitoring Platform

A complete beginner-friendly hackathon MVP using React, Spring Boot, MySQL and JWT security.

## Requirements
- Java 21
- Maven 3.9+
- MySQL 8+
- Node.js 20+

## 1. Database
Start MySQL and make sure the `root` user password is `0000`.
The application creates the `project_monitor` database automatically. If your MySQL setup does not allow that, run:

```sql
CREATE DATABASE project_monitor;
```

## 2. Backend

```bash
cd backend
mvn clean spring-boot:run
```

API: http://localhost:8080
Health: http://localhost:8080/api/health

The backend automatically creates tables with JPA and seeds:
- email: `admin@projectmonitor.com`
- password: `admin123`
- role: ADMIN

## 3. Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Open the Vite URL shown in the terminal, normally http://localhost:5173.

## Main APIs

### Public
- POST `/api/auth/login`
- POST `/api/auth/register`
- GET `/api/health`

### JWT required
- GET `/api/dashboard`
- GET/POST `/api/projects`
- GET/PUT/DELETE `/api/projects/{id}`
- GET/POST `/api/tasks`
- GET `/api/tasks/project/{projectId}`
- PUT/DELETE `/api/tasks/{id}`
- GET `/api/users`

Use header: `Authorization: Bearer <token>`.

## Postman quick test
1. POST `/api/auth/login`
```json
{"email":"admin@projectmonitor.com","password":"admin123"}
```
2. Copy `token`.
3. Authorization -> Bearer Token -> paste token.
4. POST `/api/projects`
```json
{
  "name":"Digital India Portal",
  "description":"Demo project",
  "startDate":"2026-09-03",
  "endDate":"2026-12-31",
  "budget":5000000,
  "status":"IN_PROGRESS"
}
```
5. POST `/api/tasks`
```json
{
  "title":"Build authentication module",
  "description":"Implement secure login",
  "deadline":"2026-09-15",
  "priority":"HIGH",
  "status":"PENDING",
  "projectId":1,
  "assignedToId":1
}
```
6. GET `/api/dashboard`

## Important
This is an MVP designed to be completed and demonstrated in a short hackathon. Before production use, move the database password and JWT secret into environment variables, add refresh tokens, stronger validation, audit logging, pagination, and stricter role permissions.
