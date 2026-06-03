**Employee Microservices System**

A production-grade backend system built using Spring Boot based microservices architecture. This project demonstrates real-world design patterns including authentication, inter-service communication, and fault tolerance.

---

##  Architecture Overview

This system consists of multiple independent microservices:

- **Auth Service**
  - Handles user registration and authentication
  - Generates and validates JWT tokens

- **Employee Service**
  - Manages employee-related operations
  - Secured using JWT-based authentication
  - Communicates with Auth Service for user validation

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security (JWT Authentication)
- Spring Data JPA (Hibernate)
- MySQL
- OpenFeign (Inter-service Communication)
- Resilience4j (Circuit Breaker, Retry)
- Maven

---

##  Security

- JWT-based authentication implemented across services
- Custom JWT filters for request validation
- Role-based access structure (extensible)

---

##  Inter-Service Communication

- Uses **OpenFeign Client**
- Employee Service communicates with Auth Service via REST API

---

##  Fault Tolerance (Resilience4j)

Implemented resilience patterns:

-  Circuit Breaker
-  Retry Mechanism
-  Fallback Handling

Handles scenarios where dependent services are unavailable.

---

## Project Structure

employee-microservices/
│
├── auth-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── security
│
├── employee-service
│   ├── controller
│   ├── service
│   ├── repository
│   ├── feign client
│   ├── security

---

### Run Services

#### 1️⃣ Auth Service

bash
cd auth-service
mvn spring-boot:run


cd employee-service
mvn spring-boot:run

---

API Flow

User authenticates via Auth Service
Auth Service generates JWT token
Client sends requests with JWT to Employee Service
Employee Service validates user via Auth Service (Feign call)
If Auth Service is unavailable → fallback logic is triggered

---

 Features Implemented

- Microservices-based architecture
- JWT authentication & authorization
- Inter-service communication using OpenFeign
- Circuit Breaker & Retry pattern (Resilience4j)
- Fallback handling for failure scenarios
- Layered architecture (Controller → Service → Repository)
- Secure APIs with custom JWT filters

---

 Future Enhancements

API Gateway (Spring Cloud Gateway)
Service Discovery (Eureka)
Centralized Configuration (Spring Cloud Config)
Rate Limiting & Bulkhead (Resilience4j advanced patterns)
Distributed tracing & monitoring (Zipkin / Prometheus / Grafana)


