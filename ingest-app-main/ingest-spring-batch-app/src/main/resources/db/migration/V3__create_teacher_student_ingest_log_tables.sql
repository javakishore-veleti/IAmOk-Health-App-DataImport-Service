-- Teacher Ingestion Log Table
CREATE TABLE teacher_ingest_log (
  id VARCHAR(36) PRIMARY KEY,
  source_file_path TEXT NOT NULL,
  total_records INT NOT NULL,
  success_count INT DEFAULT 0,
  failure_count INT DEFAULT 0,
  ingest_status VARCHAR(50) NOT NULL, -- e.g. STARTED, COMPLETED, FAILED
  error_summary TEXT,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(100),
  updated_by VARCHAR(100),
  version BIGINT NOT NULL DEFAULT 0
);

-- Student Ingestion Log Table
CREATE TABLE student_ingest_log (
  id VARCHAR(36) PRIMARY KEY,
  source_file_path TEXT NOT NULL,
  total_records INT NOT NULL,
  success_count INT DEFAULT 0,
  failure_count INT DEFAULT 0,
  ingest_status VARCHAR(50) NOT NULL, -- e.g. STARTED, COMPLETED, FAILED
  error_summary TEXT,

  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(100),
  updated_by VARCHAR(100),
  version BIGINT NOT NULL DEFAULT 0
);
