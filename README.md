# Campus Facility Booking and Maintenance API

A comprehensive REST API for managing campus facility bookings and maintenance tickets. This application enables users to book facilities, manage bookings, and track maintenance issues efficiently.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Authentication](#authentication)
- [Validation](#validation)
- [Error Handling](#error-handling)
- [Testing with Postman](#testing-with-postman)
- [Future Enhancements](#future-enhancements)

---

## 🎯 Project Overview

The Campus Facility Booking and Maintenance API is a Spring Boot-based REST service designed for academic institutions to:
- Manage facility bookings (classrooms, labs, auditoriums, etc.)
- Track maintenance requests with priority levels
- Handle user authentication and authorization
- Support role-based access control (Admin, Staff, Student)

---

## ✨ Features

### Facility Management
- Create, read, update, and delete facilities
- Categorize facilities by type (CLASSROOM, LAB, AUDITORIUM, etc.)
- Filter facilities by type and availability status
- View facility capacity and location information

### Booking System
- Book facilities with time slot conflict detection
- Automatic conflict checking to prevent double bookings
- Booking status tracking (PENDING, APPROVED, REJECTED, CANCELLED)
- Approve/reject bookings (Admin only)
- Cancel bookings (User)
- Filter bookings by user, facility, date, and status

### Maintenance Ticketing
- Create maintenance tickets with priority levels
- Track ticket status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- Assign tickets to maintenance staff
- Filter tickets by facility, status, priority, and assigned users
- Mark tickets as resolved with timestamp

### User Management
- User registration with role assignment
- Role-based access control (Admin, Staff, Student)
- User profile management
- Password management with secure hashing (BCrypt)
- User authentication and session management

### Security
- Spring Security integration
- BCrypt password encryption
- Role-based endpoint protection
- CSRF protection disabled for API testing
- Session-based authentication

---

## 🛠 Tech Stack

- **Backend Framework:** Spring Boot 4.0.2
- **Java Version:** Java 25
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Security:** Spring Security with BCrypt
- **Build Tool:** Maven 3.x
- **API Testing:** Postman
- **Logging:** SLF4J with Logback (via Spring Boot)
- **Project Lombok:** For reducing boilerplate code

### Dependencies
```xml
- spring-boot-starter-web (REST API)
- spring-boot-starter-data-jpa (Database access)
- spring-boot-starter-security (Authentication & Authorization)
- postgresql (Database driver)
- lombok (Code generation)
- jakarta.validation (Input validation)
```

---

## 📦 Prerequisites

Before running this project, ensure you have:

1. **Java 21 or higher** installed
   ```bash
   java -version
   ```

2. **Maven 3.6+** installed
   ```bash
   mvn -version
   ```

3. **PostgreSQL 12+** installed and running
   ```bash
   psql --version
   ```

4. **Git** for version control

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd Campus_facality_booking_and_mantenance_API
```

### Step 2: Create PostgreSQL Database
```bash
psql -U postgres

# In PostgreSQL terminal:
CREATE DATABASE facility_booking;
```

### Step 3: Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/facility_booking
spring.datasource.username=postgres
spring.datasource.password=<YOUR_PASSWORD>
```

### Step 4: Build the Project
```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the source code
- Run integration tests
- Package the application

---

## ⚙️ Configuration

### Application Properties

**Key configurations in `application.properties`:**

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/facility_booking
spring.datasource.username=postgres
spring.datasource.password=db@1234
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging Configuration
logging.level.root=warn
logging.level.com.cfbmapi=info

# Spring Security
spring.security.user.name=user
spring.security.user.password=password
```

### Database Auto-Creation
- `spring.jpa.hibernate.ddl-auto=update` automatically creates and updates tables on application startup
- Existing data is preserved when updating schema

---

## ▶️ Running the Application

### Option 1: Using Maven
```bash
mvn spring-boot:run
```

### Option 2: Using Java JAR
```bash
# Build the JAR file
mvn clean package

# Run the JAR
java -jar target/Campus_facality_booking_and_mantenance_API-1.0-SNAPSHOT.jar
```

### Successful Startup Indicators
```
INFO ... FacilityBookingApplication : Started FacilityBookingApplication in X.XXX seconds
```

The API will be available at: `http://localhost:8080`

---

## 📡 API Endpoints

### Authentication Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/auth/login` | Login user | No |
| POST | `/api/auth/logout` | Logout user | Yes |

### User Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/users/register` | Register new user | No |
| GET | `/api/users` | Get all users | Yes |
| GET | `/api/users/{id}` | Get user by ID | Yes |
| GET | `/api/users/email/{email}` | Get user by email | Yes |
| GET | `/api/users/roles/{role}` | Get users by role | Yes |
| PUT | `/api/users/{id}` | Update user profile | Yes |
| DELETE | `/api/users/{id}` | Delete user | Yes |
| PUT | `/api/users/{id}/change-password` | Change password | Yes |

### Facility Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/facilities` | Create facility | Yes |
| GET | `/api/facilities` | Get all facilities | Yes |
| GET | `/api/facilities/{id}` | Get facility by ID | Yes |
| GET | `/api/facilities/type/{type}` | Get facilities by type | Yes |
| GET | `/api/facilities/available` | Get available facilities | Yes |
| PUT | `/api/facilities/{id}` | Update facility | Yes |
| PUT | `/api/facilities/{id}/status` | Update facility status | Yes |
| DELETE | `/api/facilities/{id}` | Delete facility | Yes |

### Booking Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/bookings` | Create booking | Yes |
| GET | `/api/bookings` | Get all bookings | Yes |
| GET | `/api/bookings/{id}` | Get booking by ID | Yes |
| PUT | `/api/bookings/{id}` | Update booking | Yes |
| PUT | `/api/bookings/{id}/approve` | Approve booking (Admin) | Yes |
| PUT | `/api/bookings/{id}/reject` | Reject booking (Admin) | Yes |
| PUT | `/api/bookings/{id}/cancel` | Cancel booking | Yes |

### Maintenance Ticket Endpoints
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/api/tickets` | Create ticket | Yes |
| GET | `/api/tickets` | Get all tickets | Yes |
| GET | `/api/tickets/{id}` | Get ticket by ID | Yes |
| GET | `/api/tickets/facility/{facilityId}` | Get tickets by facility | Yes |
| GET | `/api/tickets/status/{status}` | Get tickets by status | Yes |
| PUT | `/api/tickets/{id}` | Update ticket | Yes |
| PUT | `/api/tickets/{id}/status` | Update ticket status | Yes |
| DELETE | `/api/tickets/{id}` | Delete ticket | Yes |

---

## 📊 Database Schema

### Key Tables

#### Users Table
- `id` (Primary Key)
- `name` (String)
- `email` (Unique)
- `password` (BCrypt Encrypted)
- `role` (ENUM: ADMIN, STAFF, STUDENT)
- `active` (Boolean)
- `created_at` (Timestamp)

#### Facilities Table
- `id` (Primary Key)
- `name` (String)
- `type` (ENUM: CLASSROOM, LAB, AUDITORIUM, etc.)
- `capacity` (Integer)
- `location` (String)
- `status` (ENUM: AVAILABLE, UNDER_MAINTENANCE, UNAVAILABLE)
- `created_at` (Timestamp)

#### Bookings Table
- `id` (Primary Key)
- `user_id` (Foreign Key → Users)
- `facility_id` (Foreign Key → Facilities)
- `booking_date` (Date)
- `start_time` (Time)
- `end_time` (Time)
- `purpose` (String)
- `status` (ENUM: PENDING, APPROVED, REJECTED, CANCELLED)
- `created_at` (Timestamp)

#### Maintenance Tickets Table
- `id` (Primary Key)
- `title` (String)
- `description` (Text)
- `facility_id` (Foreign Key → Facilities)
- `reported_by_user_id` (Foreign Key → Users)
- `assigned_to_user_id` (Foreign Key → Users)
- `priority` (ENUM: LOW, MEDIUM, HIGH, CRITICAL)
- `status` (ENUM: OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- `created_at` (Timestamp)
- `resolved_at` (Timestamp, nullable)

---

## 📁 Project Structure

```
Campus_facality_booking_and_mantenance_API/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cfbmapi/
│   │   │       ├── FacilityBookingApplication.java (Entry Point)
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java (Spring Security Config)
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── UserController.java
│   │   │       │   ├── FacilityController.java
│   │   │       │   ├── BookingController.java
│   │   │       │   └── MaintenanceTicketController.java
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── UserService.java
│   │   │       │   ├── FacilityService.java
│   │   │       │   ├── BookingService.java
│   │   │       │   └── MaintenanceTicketService.java
│   │   │       ├── repository/
│   │   │       │   ├── UserRepository.java
│   │   │       │   ├── FacilityRepository.java
│   │   │       │   ├── BookingRepository.java
│   │   │       │   └── MaintenanceTicketRepository.java
│   │   │       ├── entity/
│   │   │       │   ├── User.java
│   │   │       │   ├── Facility.java
│   │   │       │   ├── Booking.java
│   │   │       │   ├── MaintenanceTicket.java
│   │   │       │   └── Enums (UserRole, BookingStatus, etc.)
│   │   │       ├── dto/
│   │   │       │   ├── LoginRequest.java
│   │   │       │   ├── ErrorResponse.java
│   │   │       │   └── Other DTOs
│   │   │       └── exception/
│   │   │           └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── postman/
│   ├── collections/ (API Collections)
│   ├── environments/ (Environment Variables)
│   └── globals/ (Global Variables)
├── pom.xml (Maven Configuration)
└── README.md
```

---

## 🔐 Authentication

### Login Process
1. **POST** to `/api/auth/login` with credentials:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

2. **Response:**
```json
{
  "userId": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "role": "STUDENT"
}
```

3. Session is created automatically (JSESSIONID cookie)

### Default Credentials
- **Username:** `user`
- **Password:** `password`

### Authorization
- Routes require Spring Security authentication
- Role-based access control via annotations (e.g., `@PreAuthorize("hasRole('ADMIN')")`)

---

## ✅ Validation

All DTOs include validation using Jakarta Validation (formerly Bean Validation):

### Example: BookingCreateRequest
- `userId`: Must be positive
- `facilityId`: Must be positive
- `date`: Required, must be today or future date
- `startTime`: Required
- `endTime`: Required
- `purpose`: Required, 3-500 characters

### Example: UserRegisterRequest
- `name`: Required, 2-100 characters
- `email`: Required, must be valid email format
- `password`: Required, 6-100 characters
- `role`: Required, must be valid UserRole enum

**Validation Error Response:**
```json
{
  "status": 400,
  "message": "Validation failed",
  "error": "Invalid input provided",
  "timestamp": "2026-03-30T10:30:00",
  "path": "/api/bookings",
  "validation_errors": {
    "date": "Booking date must be today or in the future",
    "purpose": "Purpose must be between 3 and 500 characters"
  }
}
```

---

## 🚨 Error Handling

### Global Exception Handler
All exceptions are handled uniformly through `GlobalExceptionHandler.java`

### Error Response Format
```json
{
  "status": 400,
  "message": "Operation failed: Resource not found",
  "error": "BadRequest",
  "timestamp": "2026-03-30T10:30:00",
  "path": "/api/facilities/999"
}
```

### HTTP Status Codes
- **200 OK** - Success
- **201 CREATED** - Resource created successfully
- **400 BAD REQUEST** - Invalid input or validation failed
- **401 UNAUTHORIZED** - Authentication required
- **404 NOT FOUND** - Resource not found
- **500 INTERNAL SERVER ERROR** - Server error

---

## 🧪 Testing with Postman

### Importing Postman Collection
1. Open Postman
2. Import collection from `postman/collections/`
3. Set up environment variables in `postman/environments/`
4. Use global variables from `postman/globals/workspace.globals.yaml`

### Sample Test Flow
1. **Register User:** POST `/api/users/register`
2. **Login:** POST `/api/auth/login`
3. **Create Facility:** POST `/api/facilities`
4. **Get Facilities:** GET `/api/facilities`
5. **Create Booking:** POST `/api/bookings`
6. **Approve Booking:** PUT `/api/bookings/{id}/approve`

---

## 🔮 Future Enhancements

### Planned Features
- [ ] JWT Token-based Authentication (replacing session-based)
- [ ] OpenAPI/Swagger Documentation
- [ ] Unit Tests & Integration Tests
- [ ] Pagination for list endpoints
- [ ] Advanced filtering and search
- [ ] Email notifications for bookings/maintenance
- [ ] Booking cancellation/modification policies
- [ ] Facility availability calendar view
- [ ] SLA tracking for maintenance tickets
- [ ] Analytics and reporting dashboard
- [ ] Docker containerization
- [ ] CI/CD pipeline integration

### Performance Improvements
- [ ] Caching with Redis
- [ ] Database query optimization
- [ ] API rate limiting
- [ ] Async processing for notifications

---

## 📝 Logging

Logging is configured using SLF4J with Logback:

```properties
logging.level.root=warn
logging.level.com.cfbmapi=info
```

Log levels:
- **ERROR:** Critical errors that need immediate attention
- **WARN:** Warning messages for potential issues
- **INFO:** General informational messages
- **DEBUG:** Detailed debug information

---

## 🤝 Contributing

To contribute to this project:

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -m 'Add feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Submit a pull request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 👨‍💻 Authors

- **Project:** Campus Facility Booking and Maintenance System
- **Institution:** DDU (Semester 4)
- **Version:** 1.0-SNAPSHOT

---

## 📞 Support & Troubleshooting

### Common Issues

**Issue: Database Connection Failed**
```
Solution: Verify PostgreSQL is running and credentials are correct in application.properties
```

**Issue: Port 8080 Already in Use**
```
Solution: Change port in application.properties: server.port=8081
```

**Issue: Compilation Error with Java Version**
```
Solution: Ensure Java 21+ is installed and JAVA_HOME is set correctly
```

---

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Jakarta Validation API](https://jakarta.ee/specifications/validation/)

---

**Last Updated:** March 30, 2026
**Status:** Active Development
