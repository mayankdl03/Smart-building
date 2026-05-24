# Honeywell Smart Building IoT API

A high-performance, fault-tolerant RESTful API built with **Spring Boot 3** and **Java 17** for managing and monitoring IoT sensors in smart buildings. 

This project demonstrates advanced software architecture patterns suitable for enterprise-grade applications, specifically targeted for IoT and Telemetry use cases.

<!-- PLACEHOLDER FOR MAIN BANNER IMAGE: Add your banner image here -->
![Project Banner Banner Placeholder](https://via.placeholder.com/1200x300.png?text=Honeywell+Smart+Building+IoT+API)

## 🏗️ System Architecture

### High-Level Architecture Diagram
<!-- GitHub will render this Mermaid diagram automatically! -->
```mermaid
graph TD
    Client[Client App / Postman] -->|REST API| Controller(Sensor Controller)
    Controller --> Service(Sensor Service)
    
    Service -->|AOP Interceptor| Profiler[Logging Aspect]
    Service -->|Spring Cache| Cache[(RAM Cache)]
    Service -.->|Simulated Sync with Retry| Cloud[Honeywell Cloud]
    
    Service -->|Hibernate ORM| Repo(Sensor & Reading Repositories)
    Repo --> DB[(H2 Database)]
    
    Job(Scheduled Anomaly Job) -->|Scan Data| Repo
    Job -->|Publish| Event[Sensor Anomaly Event]
    Event --> Listener[Anomaly Alert Listener]
```

<!-- PLACEHOLDER FOR ARCHITECTURE IMAGE: If you generate a custom diagram image, place it here -->
<!-- ![System Architecture](docs/architecture.png) -->

## 🌟 Key Features & Architecture

- **Event-Driven Architecture (EDA):** Uses Spring `ApplicationEventPublisher` to decouple anomaly detection from alert notification listeners.
- **Aspect-Oriented Programming (AOP):** Implements cross-cutting concerns (`@Aspect`, `@Around`) for automated API performance profiling and execution time logging.
- **Fault Tolerance & Circuit Breaking:** Leverages Spring Retry (`@Retryable`, `@Recover`) to handle transient network failures when syncing data with external cloud providers.
- **Data Caching:** Integrates Spring Cache (`@Cacheable`, `@CacheEvict`) to reduce database load for frequent sensor data reads.
- **JPA Auditing & Telemetry:** Tracks sensor history (`@OneToMany`) and automates auditing (`@CreatedDate`, `@LastModifiedDate`).
- **Data Transfer Objects (DTO):** Isolates internal database entities from the exposed API using the DTO design pattern.

## 🛠️ Technology Stack
- **Framework:** Spring Boot 3.2.x, Java 17
- **Database:** H2 In-Memory Database / Hibernate ORM
- **API Documentation:** OpenAPI 3.0 (Swagger UI)
- **Tooling:** Maven, Spring Actuator (DevOps Monitoring)

## 🚀 How to Run Locally

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

## 📂 API Endpoints
- `GET /api/sensors` - Retrieve all sensors (Paginated)
- `GET /api/sensors/{id}` - Retrieve sensor details (Cached)
- `POST /api/sensors` - Register a new sensor
- `GET /api/sensors/{id}/average-reading` - Get telemetry analytics
- `POST /api/sensors/{id}/sync` - Push to cloud (Simulates Fault Tolerance/Retry)
