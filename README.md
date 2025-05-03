# IAmOk Health App Data Import Service
Data import service for the IAmOK Health Status application that supports batch ingestion of teacher and student data using multiple technologies like Spring Batch and cloud-native orchestration (e.g., AWS Step Functions, Azure AKS, GCP Workflows), with built-in observability and DevOps automation.

## Overview

This repository contains the data import service for the [IAmOK Health Status Reporting System](https://ieeexplore.ieee.org/document/9361238), designed to ingest and enrich teacher and student data from external sources such as local folders, AWS S3, GCS, or Azure Blob Storage. The service supports multiple ingestion technologies (e.g., Spring Batch, AWS Step Functions, Azure AKS), integrates with dependent services, and ensures full observability and DevOps automation.


## Inspiration

This project is inspired by the paper:

**"Design and Implementation of a Health Status Reporting System Based on Spring Boot"**  
Publisher: IEEE  
[IEEE Xplore Article →](https://ieeexplore.ieee.org/document/9361238)  
Authors: Zhe Wang, Zhu Liang Yu, Feng Tang


## Purpose of This Implementation

As part of academic research in **Observability in Distributed Systems**, this project demonstrates:

- Observability-aware batch ingestion architectures
- Integration with Spring Batch and cloud-native orchestration
- Auditability and traceability in asynchronous workflows
- DevOps automation and real-world deployment patterns


## Observability Practices for Batch Ingest Applications

### Key Observability Practices

| Category     | Implementation |
|--------------|----------------|
| **Metrics**  | Micrometer metrics exposed via `/actuator/prometheus` |
| **Logging**  | Structured logs with `ingest_log_id`, step/job context |
| **Tracing**  | Optional OpenTelemetry or Spring Sleuth integration |
| **Health Checks** | Actuator endpoints for liveness/readiness |
| **Audit Trail** | All records tagged with `ingest_log_id` |
| **Resilience** | Retry & skip policies per Spring Batch step |

### Research Highlights

- Real-time logging and metrics with `JobExecutionListener`
- Traceability using correlation IDs (`ingest_log_id`)
- Grafana dashboards for job duration and failure trends
- Prometheus alerting on ingestion failures or slowdowns

### Practical Observability Tips

- Use `JobExecutionListener` to emit logs and metrics
- Correlate logs, DB entries, and traces with `ingest_log_id`
- Define alerts for job duration, error rates, and retry spikes
- Export metrics to Prometheus and visualize with Grafana

> Note: This service focuses purely on data ingestion and enrichment. Business logic validation is handled elsewhere.


## Scope of This Service

- Ingests teacher/student CSV data from cloud/local folders
- Enriches input by querying downstream services
- Writes normalized data to PostgreSQL
- Tracks every batch job via `ingest_log_id`
- Exposes APIs to trigger ingestion jobs asynchronously


## Dependent Services

These downstream microservices are mocked locally via WireMock:

| Service                   | Purpose                                |
|---------------------------|----------------------------------------|
| TeacherProfileService     | Maps teacher name/email to `teacher_id`|
| TeachingService           | Returns teaching history               |
| StudentProfileService     | Maps student email to `student_id`     |
| StudentLearningService    | Returns learning/course records        |


## Getting Started

### Prerequisites

- Java 17+
- Docker & Docker Compose
- Maven 3.8+
- Node.js + npm

### 🔧 Setup

First-time setup:
```bash
npm install
```

Start containers (before each session):
```bash
npm run dev:setup-local-containers
```

Start the Spring Boot API server:
```bash
npm run start-spring-boot-app
```

### Verifying Local Postgres Initialization

To verify that your local PostgreSQL container has successfully created the `iam_ok_health_db` database, run the following command from your terminal:

```bash
docker exec -it iamok-health-app-postgres psql -U iamok -d postgres -c '\l'
```

This command:
- Enters the running PostgreSQL container
- Uses the psql client as user iamok
- Connects to the default postgres database
- Lists all available databases (\l)

Expected Output:
Look for a line that contains:

```bash
# iam_ok_health_db | iamok | UTF8 | ...
```

## Triggering Ingestion via REST API

### API Endpoints

- `POST /api/ingest/teacher`
- `POST /api/ingest/student`

### JSON Payload

```json
{
  "fileUri": "path-to-folder-containing-csv-files",
  "storageProviderType": "local | s3 | gcs | azure"
}
```

- `fileUri`: Path to a folder containing CSV files (not a single file)
- `storageProviderType`:
  - `local`: local file system path
  - `s3`: AWS S3
  - `gcs`: Google Cloud Storage
  - `azure`: Azure Blob Storage

### Behavior

1. API validates your input
2. Batch job is triggered asynchronously
3. Response includes `ingest_log_id`
4. Logs, metrics, and audit entries are tagged with this ID


## Observability Features

### Integrated Tools

| Tool             | Purpose                           | Spring Support                          |
|------------------|-----------------------------------|------------------------------------------|
| Prometheus       | Metric scraping                   | Micrometer + `/actuator/prometheus`     |
| Grafana          | Visualization                     | Dashboards for job durations, failures  |
| Elasticsearch    | Centralized log storage/search    | With Logstash or Filebeat               |
| OpenTelemetry    | Distributed tracing               | Optional via Sleuth/OTel SDKs           |

> Sample dashboards and alert rules are available in `docs/observability/`


## Cloud Platform Support

| Cloud   | Workflow Engine       | Deployment Target     |
|---------|------------------------|------------------------|
| AWS     | Step Functions         | ECS / EKS / Lambda     |
| Azure   | Durable Functions / AKS| Azure Kubernetes       |
| GCP     | Cloud Workflows        | GKE                    |


## License & Citation

If you use this work in academic or enterprise contexts, please cite:

> Zhe Wang, Zhu Liang Yu, Feng Tang  
> *Design and Implementation of a Health Status Reporting System Based on Spring Boot*  
> IEEE ICAICE 2020  
> [https://ieeexplore.ieee.org/document/9361238](https://ieeexplore.ieee.org/document/9361238)
