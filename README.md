# GeoTracker Backend

Spring Boot REST API for GeoTracker, an employee attendance system that validates check-ins using GPS coordinates and an administrator-configured office geofence.

## Overview

The backend stores employees, attendance records, and the office geofence in MongoDB. An employee can mark attendance by sending their current latitude and longitude. The service accepts the check-in only when:

1. The request is made during office hours (`09:00` to `18:00`).
2. The office geofence has been configured.
3. The submitted coordinates are within the configured radius.
4. The employee has not already marked attendance for the current date.

Check-ins after `09:15` are saved with the `LATE` status; earlier valid check-ins use `PRESENT`.

## Features

- Employee creation, lookup, listing, partial update, and deletion
- Admin configuration of one office geofence
- Haversine distance calculation in meters
- GPS-based attendance marking
- Office-hours validation and late-arrival detection
- Prevention of duplicate attendance for the same employee and date
- Employee attendance history
- Attendance filtering by month, date range, and status
- Admin views for all employees' attendance or one employee's attendance
- MongoDB persistence through Spring Data MongoDB
- CORS configured for browser/mobile clients
- Docker image and Maven Wrapper support
- Startup seed data for `EMP101` when the employee collection is empty

## Technology Stack

| Area | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 4.0.2 |
| Web | Spring MVC / REST |
| Database | MongoDB |
| Data access | Spring Data MongoDB |
| Security dependencies | Spring Security, JJWT 0.11.5 |
| Build | Maven |
| Containerization | Docker, Eclipse Temurin 17 Alpine |
| Code generation | Lombok |

## Project Structure

```text
src/
├── main/java/com/geotracker/backend/
│   ├── config/          # CORS, MongoDB, Spring Security, JWT filter
│   ├── controller/      # REST endpoints
│   ├── model/           # MongoDB documents and request models
│   ├── repositories/    # Spring Data MongoDB repositories
│   ├── scheduler/       # Reserved/inactive scheduler implementation
│   ├── service/         # Business logic
│   └── util/            # Distance, time, and JWT utilities
├── main/resources/
│   └── application.properties
└── test/
    └── java/            # Spring Boot context test
```

## API Reference

The default base URL is `http://localhost:8080`.

### Employee APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/employees` | Create an employee |
| `GET` | `/employees` | List all employees |
| `GET` | `/employees/{empId}` | Get an employee by employee ID |
| `GET` | `/employees/{empId}/profile` | Get an employee profile |
| `PUT` | `/employees/update?empId=EMP101&name=Updated Name` | Partially update employee fields |
| `DELETE` | `/employees/delete?empId=EMP101` | Delete an employee |

Create employee example:

```json
{
  "empId": "EMP102",
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "+1-555-0100",
  "department": "Engineering",
  "role": "EMPLOYEE",
  "post": "Software Engineer"
}
```

### Employee Attendance APIs

| Method | Endpoint | Description |
| --- | --- | --- |
| `POST` | `/attendance/mark?empId=EMP101&lat=21.0077&lon=75.5626` | Mark today's attendance |
| `GET` | `/attendance/history?empId=EMP101` | Get attendance history |
| `GET` | `/attendance/history/month?empId=EMP101&month=3&year=2026` | Filter history by month |
| `GET` | `/attendance/history/range?empId=EMP101&startDate=2026-03-01&endDate=2026-03-18` | Filter history by date range |
| `GET` | `/attendance/history/status?empId=EMP101&status=LATE` | Filter history by status |
| `GET` | `/attendance/history/filter?empId=EMP101&status=LATE&startDate=2026-03-01&endDate=2026-03-18` | Filter by status and date range |

The mark endpoint returns a plain-text result such as:

```text
Attendance marked: PRESENT
Attendance marked: LATE
Outside office hours
Outside office location
Attendance already marked
```

### Admin APIs

#### Geofence

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/admin/geofence` | Get the configured office geofence |
| `POST` | `/admin/geofence` | Create or update the single office geofence |

Example request:

```json
{
  "latitude": 21.0077,
  "longitude": 75.5626,
  "radius": 150
}
```

`radius` is measured in meters. The geofence endpoint uses an upsert-style implementation: the first stored geofence is updated on subsequent requests.

#### Attendance reporting

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/admin/attendance` | Get all attendance records |
| `GET` | `/admin/attendance/all/month?month=3&year=2026` | Get all records for a month |
| `GET` | `/admin/attendance/all/range?startDate=2026-03-01&endDate=2026-03-18` | Get all records in a date range |
| `GET` | `/admin/attendance/all/status?status=LATE` | Get all records by status |
| `GET` | `/admin/attendance/all/filter?status=LATE&startDate=2026-03-01&endDate=2026-03-18` | Filter all records by status and date range |
| `GET` | `/admin/attendance/{empId}` | Get one employee's attendance |
| `GET` | `/admin/attendance/{empId}/month?month=3&year=2026` | Get one employee's monthly attendance |
| `GET` | `/admin/attendance/{empId}/range?startDate=2026-03-01&endDate=2026-03-18` | Get one employee's attendance in a date range |
| `GET` | `/admin/attendance/{empId}/status?status=LATE` | Get one employee's attendance by status |

### Authentication endpoint

`GET /api/auth/yash` is currently a diagnostic endpoint. A login endpoint is present only as commented-out code, and the active security configuration permits all requests. `JwtUtil` and `JWTFilter` exist in the project, but JWT authentication is not currently enforced. Treat the current configuration as development-only until authentication and authorization are completed.

## Data Model

### Employee

MongoDB collection: `employees`

Fields: `id`, `empId`, `name`, `email`, `phone`, `department`, `role`, and `post`.

### Attendance

MongoDB collection: `attendance`

Fields: `id`, `empId`, `empName`, `date`, `checkInTime`, `checkOutTime`, and `status`.

Supported statuses currently used by the service are `PRESENT` and `LATE`. The model comment also mentions `ABSENT`, but absent records are not generated by the current implementation.

### Geofence

MongoDB collection: `geofence`

Fields: `id`, `latitude`, `longitude`, and `radius` in meters. The service expects one office geofence.

## Local Setup

### Prerequisites

- Java 17
- MongoDB instance or MongoDB Atlas database
- Git

### Configure the database

Set the MongoDB URI through an environment-specific configuration or a local, untracked properties file. Do not commit credentials or secrets.

Example:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/geotracker
jwt.secret=replace-with-a-long-random-secret
jwt.expiration=86400000
```

The application currently uses the `geotracker` database in `MongoConfig`.

### Run with Maven Wrapper

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

The API starts on port `8080` unless `server.port` is configured.

### Build and test

```bash
./mvnw clean test
```

Create the executable JAR:

```bash
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

## Docker

Build and run:

```bash
docker build -t geotracker-backend .
docker run --rm -p 8080:8080 geotracker-backend
```

The image builds the project with Maven and runs the generated Spring Boot JAR. Ensure the application can reach the configured MongoDB instance from the container.

## Startup Seed Data

`DataSeeder` inserts the following employee only when the `employees` collection is empty:

```text
Employee ID: EMP101
Name: Test Employee
```

Configure the office geofence with `POST /admin/geofence` before trying to mark attendance.

## Important Implementation Notes

- CORS currently allows all origins and the controller endpoints are publicly accessible.
- The default properties file contains environment-specific database and JWT settings; replace them with environment variables or an ignored local configuration before deployment.
- The active `JWTFilter` assigns the fixed employee ID `EMP101` for testing rather than validating a bearer token.
- The scheduled attendance/auto-checkout implementation is commented out and is not active.
- There is no OpenAPI/Swagger configuration in the current project.
- Date and time calculations use the server's local timezone.

## Related Project

The backend is intended to be consumed by the GeoTracker client application:

- [GeoTracker Flutter app](https://github.com/YashAthawale07/GeoTracker_Flutter_app)

## License

No license file is currently included in this repository.
