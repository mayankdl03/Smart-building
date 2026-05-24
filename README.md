# Smart Building IoT API

A high-performance, fault-tolerant RESTful API built with **Spring Boot 3** and **Java 17** for managing and monitoring IoT sensors in smart buildings. 

This project demonstrates advanced software architecture patterns suitable for enterprise-grade applications, specifically targeted for IoT and Telemetry use cases.

<!-- PLACEHOLDER FOR MAIN BANNER IMAGE: Add your banner image here -->
![Project Banner Banner Placeholder](https://via.placeholder.com/1200x300.png?text=Smart+Building+IoT+API)

## System Architecture

<!-- OVERALL ARCHITECTURE IMAGE PLACEHOLDER -->
![Overall System Architecture](docs/overall-architecture.png)
*(Replace this placeholder with the Overall Architecture Image)*

### High-Level Architecture Flow
<!-- GitHub will render this Mermaid diagram automatically! -->
```mermaid
graph TD
    Client[Client App] -->|REST API| Controller(Sensor Controller)
    Controller --> Service(Sensor Service)
    Service -->|AOP Interceptor| Profiler[Logging Aspect]
    Service -->|Spring Cache| Cache[(RAM Cache)]
    Service -.->|Simulated Sync with Retry| Cloud[External Cloud API]
    Service -->|Hibernate ORM| Repo(Sensor Repository)
    Repo --> DB[(H2 Database)]
    Job(Scheduled Anomaly Job) -->|Scan Data| Repo
    Job -->|Publish| Event[Sensor Anomaly Event]
    Event --> Listener[Anomaly Alert Listener]
```

## Key Features & Component Flows

### 1. Event-Driven Architecture (EDA)
Uses Spring `ApplicationEventPublisher` to decouple anomaly detection from alert notification listeners. When a sensor detects high temperatures, an event is published asynchronously rather than blocking the main thread.

### 2. Fault Tolerance & Circuit Breaking
Leverages Spring Retry (`@Retryable`, `@Recover`) to handle transient network failures when syncing data with external cloud providers. If the External Cloud API is down, the system retries 3 times before triggering a fallback recovery method.

### 3. Aspect-Oriented Programming (AOP)
Implements cross-cutting concerns (`@Aspect`, `@Around`) for automated API performance profiling and execution time logging without cluttering business logic.

### 4. Data Caching & Telemetry
Integrates Spring Cache (`@Cacheable`, `@CacheEvict`) to reduce database load for frequent sensor data reads. Tracks sensor history (`@OneToMany`) and automates auditing (`@CreatedDate`, `@LastModifiedDate`).

## 🛠️ Technology Stack
- **Framework:** Spring Boot 3.2.x, Java 17
- **Database:** H2 In-Memory Database / Hibernate ORM
- **API Documentation:** OpenAPI 3.0 (Swagger UI)
- **Tooling:** Maven, Spring Actuator (DevOps Monitoring)

## How to Run Locally

1. Ensure you have **Java 17** installed.
2. Clone the repository.
3. Run the application using the Maven Wrapper:
   ```bash
   # On Windows
   .\mvnw.cmd spring-boot:run
   
   # On Mac/Linux
   ./mvnw spring-boot:run
   ```
4. Access the API Documentation and test the endpoints via Swagger UI:
   👉 **http://localhost:8080/swagger-ui.html**

<!-- PLACEHOLDER FOR SWAGGER SCREENSHOT: Add a screenshot of your Swagger UI here -->
![Swagger UI Placeholder](https://via.placeholder.com/800x400.png?text=Swagger+UI+Screenshot)

## API Endpoints
- `GET /api/sensors` - Retrieve all sensors (Paginated)
- `GET /api/sensors/{id}` - Retrieve sensor details (Cached)
- `POST /api/sensors` - Register a new sensor
- `GET /api/sensors/{id}/average-reading` - Get telemetry analytics
- `POST /api/sensors/{id}/sync` - Push to cloud (Simulates Fault Tolerance/Retry)
