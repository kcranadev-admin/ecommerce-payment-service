# Architecture Notes

## Step 1 scope

This step establishes a clean service boundary and persistence model before introducing asynchronous messaging.

```text
                   HTTP
                    |
                    v
            +----------------+
            | REST Controller |
            +-------+--------+
                    |
                    v
            +----------------+
            | Payment Service |
            | @Transactional  |
            +-------+--------+
                    |
                    v
            +----------------+
            | Spring Data JPA |
            +-------+--------+
                    |
                    v
            +----------------+
            |   PostgreSQL    |
            +----------------+
```

## Domain boundary

`PaymentController` owns HTTP concerns.

`PaymentService` owns application/use-case logic and transaction boundaries.

`PaymentRepository` owns persistence access.

`Payment` is the persistence model for this first step.

DTOs prevent the REST API from exposing JPA entities directly.

## Why PENDING is the initial state

A real payment flow normally requires an external provider and asynchronous state changes. The first step stops at `PENDING`; later steps will introduce provider simulation, Kafka events, retries, and reconciliation.

## Concurrency note

The service performs a friendly pre-check for duplicate `orderId` values and also has a database unique constraint. In a concurrent request race, the database constraint remains the final protection. Step 3 will add an explicit idempotency mechanism suitable for distributed retries.
