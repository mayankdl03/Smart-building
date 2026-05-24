<!-- PLACEHOLDER FOR BANNER IMAGE -->
<img width="1774" height="887" alt="image" src="https://github.com/user-attachments/assets/5551473f-0632-4ad6-acb9-a9e11c7b498e" />


# Smart Building IoT API

A high-performance, fault-tolerant RESTful API built with **Spring Boot 3** and **Java 17** for managing and monitoring IoT sensors in smart buildings. This project demonstrates enterprise-grade backend architecture including Caching, Event-Driven patterns, and Fault Tolerance.

## System Architecture

<img width="1692" height="929" alt="image" src="https://github.com/user-attachments/assets/5bd8133f-6c76-4c36-9f83-ca228026219f" />



### Key Architectural Patterns:
- **Event-Driven Architecture (EDA):** Asynchronous event publishing for sensor anomalies (e.g., high temperature alerts) using Spring Events.
- **Fault Tolerance & Circuit Breaking:** Graceful handling of external API failures using Spring Retry (`@Retryable`, `@Recover`).
- **Data Caching:** Optimized read operations using in-memory RAM caching (`@Cacheable`) for frequently accessed sensor data.
- **Aspect-Oriented Programming (AOP):** Centralized API performance profiling and logging using `@Aspect` and `@Around`.

## Database Schema

<!-- PLACEHOLDER FOR DATABASE SCHEMA IMAGE -->
<img width="1692" height="929" alt="image" src="https://github.com/user-attachments/assets/7f90ee51-49db-44d4-b0ac-17de419f5f26" />


The system utilizes a relational database (H2/Hibernate) with strict Auditing (`@CreatedDate`, `@LastModifiedDate`).
- **Sensor Table:** Stores IoT device metadata (Name, Type, Location, Status).
- **SensorReading Table:** A One-to-Many relationship capturing time-series telemetry data from the sensors.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3.2.x
- **Database:** H2 In-Memory Database, Spring Data JPA / Hibernate
- **API Documentation:** OpenAPI 3.0 (Swagger UI)

## How to Run Locally

1. Clone the repository and navigate to the project root.
2. Run the application using the Maven wrapper:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
3. Open your browser and access the interactive API documentation:
   👉 **http://localhost:8080/swagger-ui.html**
