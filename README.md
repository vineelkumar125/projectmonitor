# ProjectMonitor - Project Monitoring Platform

A web-based project monitoring platform built with **React, Spring Boot, MySQL, and JWT security**.

ProjectMonitor helps users manage projects, tasks, assignments, deadlines, priorities, and project progress through a centralized web application.

---

## Features

* User registration and login
* JWT-based authentication
* Role-based users
* Admin, Manager, and Member roles
* Project management
* Task management
* Task assignment
* Project dashboard
* Task priorities
* Task status tracking
* Project status tracking
* MySQL database integration
* REST APIs
* Secure password hashing
* React-based frontend
* Spring Boot backend

---

## Technology Stack

### Frontend

* React.js
* Vite
* JavaScript
* Axios
* HTML
* CSS

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* JWT

### Database

* MySQL

### Development Tools

* IntelliJ IDEA / VS Code
* Git
* GitHub
* Postman

---

# Requirements

Install the following software before running the project:

* Java 21+
* Maven 3.9+
* MySQL 8+
* Node.js 20+
* npm

Check the installed versions:

```bash
java -version
mvn -version
mysql --version
node -version
npm -version
```

---

# Project Structure

```text
projectmonitor/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/
│   │       │       └── projectmonitor/
│   │       └── resources/
│   │
│   ├── pom.xml
│   └── ...
│
├── frontend/
│   ├── src/
│   ├── package.json
│   ├── package-lock.json
│   ├── vite.config.js
│   └── ...
│
├── database.sql
├── postman_collection.json
└── README.md
```

---

# Database Setup

Start your MySQL server.

The application uses the following database:

```text
project_monitor
```

If the database does not already exist, create it manually:

```sql
CREATE DATABASE project_monitor;
```

The default MySQL port is:

```text
3306
```

Configure your database username and password in the Spring Boot configuration.

> Do not upload real database passwords, JWT secrets, or other sensitive credentials to a public GitHub repository.

---

# Backend Setup

Open a terminal in the backend directory:

```bash
cd backend
```

Build the project:

```bash
mvn clean install
```

Run the Spring Boot application:

```bash
mvn spring-boot:run
```

The backend runs at:

```text
http://localhost:8080
```

Health check:

```text
http://localhost:8080/api/health
```

Spring Boot uses JPA/Hibernate to create and update database tables according to the configured entities.

---

# Frontend Setup

Open another terminal.

Go to the frontend directory:

```bash
cd frontend
```

Install the required dependencies:

```bash
npm install
```

Start the React development server:

```bash
npm run dev
```

Vite normally runs the frontend at:

```text
http://localhost:5173
```

Open the URL shown in the terminal in your browser.

---

# Authentication

ProjectMonitor uses **JWT authentication**.

The login process is:

```text
User
 ↓
React Frontend
 ↓
Login API
 ↓
Spring Boot
 ↓
Spring Security
 ↓
JWT Token
 ↓
Authenticated Requests
```

After successful login, the backend returns a JWT token.

The frontend sends the token with protected API requests:

```http
Authorization: Bearer <token>
```

Passwords are stored using secure password hashing rather than plain text.

---

# User Roles

The application supports three user roles:

```text
ADMIN
MANAGER
MEMBER
```

The roles are used to control access to application functionality and APIs.

---

# API Endpoints

## Authentication

### Login

```http
POST /api/auth/login
```

Example:

```json
{
  "email": "admin@projectmonitor.com",
  "password": "admin123"
}
```

### Register

```http
POST /api/auth/register
```

### Health Check

```http
GET /api/health
```

---

## Dashboard

```http
GET /api/dashboard
```

Requires JWT authentication.

---

## Projects

Get all projects:

```http
GET /api/projects
```

Create a project:

```http
POST /api/projects
```

Get a project:

```http
GET /api/projects/{id}
```

Update a project:

```http
PUT /api/projects/{id}
```

Delete a project:

```http
DELETE /api/projects/{id}
```

---

## Tasks

Get all tasks:

```http
GET /api/tasks
```

Create a task:

```http
POST /api/tasks
```

Get tasks for a project:

```http
GET /api/tasks/project/{projectId}
```

Update a task:

```http
PUT /api/tasks/{id}
```

Delete a task:

```http
DELETE /api/tasks/{id}
```

---

## Users

Get users:

```http
GET /api/users
```

---

# Postman Testing

You can test the backend APIs using Postman.

## 1. Login

Send:

```http
POST http://localhost:8080/api/auth/login
```

Request body:

```json
{
  "email": "admin@projectmonitor.com",
  "password": "admin123"
}
```

The response contains a JWT token.

---

## 2. Copy the JWT Token

Copy the token returned by the login API.

In Postman:

```text
Authorization
→ Bearer Token
→ Paste Token
```

---

## 3. Create a Project

Send:

```http
POST http://localhost:8080/api/projects
```

Example request:

```json
{
  "name": "Digital India Portal",
  "description": "Demo project",
  "startDate": "2026-09-03",
  "endDate": "2026-12-31",
  "budget": 5000000,
  "status": "IN_PROGRESS"
}
```

---

## 4. Create a Task

Send:

```http
POST http://localhost:8080/api/tasks
```

Example request:

```json
{
  "title": "Build authentication module",
  "description": "Implement secure login",
  "deadline": "2026-09-15",
  "priority": "HIGH",
  "status": "PENDING",
  "projectId": 1,
  "assignedToId": 1
}
```

---

## 5. View Dashboard

Send:

```http
GET http://localhost:8080/api/dashboard
```

Make sure the JWT token is included.

---

# Application Flow

The complete application works approximately as follows:

```text
                    ProjectMonitor
                          │
             ┌────────────┴────────────┐
             │                         │
        React Frontend            Spring Boot
             │                         │
             │                    REST APIs
             │                         │
             └─────────────┬───────────┘
                           │
                    Spring Security
                           │
                      JWT Token
                           │
                    Service Layer
                           │
                    JPA / Hibernate
                           │
                       MySQL
```

---

# Security

The application uses:

* Spring Security
* JWT authentication
* BCrypt password hashing
* Stateless authentication
* Protected REST APIs
* Role-based authorization

For production deployment, sensitive configuration should be moved to environment variables or a secure secrets-management system.

Sensitive information includes:

```text
Database password
JWT secret
API keys
Other private credentials
```

---

# Future Improvements

Possible future improvements include:

* Refresh token support
* Advanced role permissions
* Audit logging
* Pagination
* Search and filtering
* Project progress charts
* Notifications
* Email notifications
* File/document management
* Advanced reports
* Production deployment
* Environment-specific configuration
* Improved validation
* Automated testing

---

# License

This project is intended for learning, development, and demonstration purposes.