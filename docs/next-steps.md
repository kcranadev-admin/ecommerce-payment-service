# Next Steps

## Step 2 — Event-driven payment flow

- transactional outbox table
- outbox publisher
- Kafka topic `payment-events`
- `PaymentProcessed` event
- consumer group for downstream services

## Step 3 — Reliability

- idempotency key / event ID
- retry policy
- dead-letter topic
- consumer failure tests

## Step 4 — Reconciliation

Add a scheduled reconciliation process comparing payment records with an external-provider simulation.

## Step 5 — Observability

Add Micrometer / OpenTelemetry traces and metrics, then document a Prometheus/Grafana/Jaeger local setup.
