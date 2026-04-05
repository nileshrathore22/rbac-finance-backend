# RBAC Finance Backend

A Spring Boot backend application for Finance Data Processing and Access Control. This project demonstrates API design, role-based access control, data modeling, and clean backend architecture.

## Tech Stack
- **Language**: Java 17
- **Framework**: Spring Boot 3.2.4
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Security**: Spring Security + JWT
- **Documentation**: OpenAPI (Swagger)

## Features
- **User and Role Management**: Create users, manage access levels (VIEWER, ANALYST, ADMIN).
- **Financial Records Management**: CRUD operations, filtering, soft-delete.
- **Dashboard Summaries**: Summary statistics (total income, expenses, category-wise totals, recent activity).
- **Access Control Details**:
  - `VIEWER`: Can view dashboard data only.
  - `ANALYST`: Can view dashboard data and financial records lists.
  - `ADMIN`: Fully authorized to manage users, roles, and perform CRUD on records.

## Setup Instructions

### 1. Database Configuration
Make sure you have Docker installed. We have provided a `docker-compose.yml` to easily start a PostgreSQL database.

```bash
docker-compose up -d
```
*This starts a PostgreSQL instance on port `5432` with username `admin` and password `adminpassword`.*

### 2. Run the Application
You can run the application directly via your IDE, or using Maven:
```bash
./mvnw spring-boot:run
```

### 3. API Documentation
Once the application is running, navigate to the local Swagger UI to test the endpoints:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Testing Flow
1. **Register**: Use the `/api/auth/register` endpoint to create a user (you can specify the `role`).
2. **Login**: Use the `/api/auth/login` endpoint to get your JWT Token.
3. **Authorize**: Click "Authorize" in Swagger UI and provide the Bearer token.
4. Now you can use the endpoints according to your User's role!

## Additional Improvements added:
- JWT Authentication
- Pagination and Sorting for Records
- Global Exception Handling
- API Documentation using Swagger
- Soft-Delete pattern for Financial Records

## Demonstration
As proof of functionality covering all assignment requirements (Authentication, RBAC, Record CRUD, and Aggregation logic), please see the automated end-to-end API demonstration below:

<img src="./screenshots/api_demo_recording.webp" alt="API Demonstration" width="800"/>
