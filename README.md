# E-commerce Payment Service

A small Spring Boot engineering sample representing the first building block of an e-commerce payment flow.

Step 1 deliberately stays synchronous: REST API + PostgreSQL persistence. Kafka, transactional outbox, idempotent consumers, retry/DLQ, and reconciliation come later.

## Architecture

```text
Client / Order Service
        |
        | POST /api/v1/payments
        v
+--------------------------+
| Payment REST Controller  |
+------------+-------------+
             |
             v
+--------------------------+
| Payment Service          |
| Transaction boundary     |
+------------+-------------+
             |
             v
+--------------------------+
| Spring Data JPA          |
+------------+-------------+
             |
             v
+--------------------------+
| PostgreSQL               |
| payments table           |
+--------------------------+
```

## Technology

- Java 17+
- Spring Boot 4.1.1
- Spring MVC
- Spring Data JPA / Hibernate
- PostgreSQL
- Spring Boot Actuator
- Gradle
- JUnit / AssertJ

## Run locally

### Start PostgreSQL

```bash
docker compose up -d postgres
```

### Start the application

```bash
./gradlew bootRun
```

Windows PowerShell:

```powershell
.\gradlew.bat bootRun
```

The service starts on `http://localhost:8081`.

### Health

```text
GET http://localhost:8081/actuator/health
```

### Create a payment

```http
POST /api/v1/payments
Content-Type: application/json

{
  "orderId": "ORD-1001",
  "customerId": "CUS-101",
  "amount": 1499.00,
  "currency": "INR"
}
```

### Get a payment

```http
GET /api/v1/payments/{paymentId}
```

## Local database

```text
host: localhost
port: 5432
database: payments
username: payments
password: payments
```

These credentials are for local development only.

## Tests

The test profile uses an in-memory H2 database in PostgreSQL compatibility mode, so the standard test run does not require PostgreSQL.

```bash
./gradlew test
```

## Project intent

This is an interview-oriented engineering sample, not a pretend production product. The repository grows one distributed-systems concern at a time so every design decision remains easy to explain.

1. Payment persistence and REST API
2. Transactional outbox + Kafka
3. Idempotent event consumption
4. Retry + dead-letter topic
5. Reconciliation
6. Observability
7. Optional container/AWS deployment
