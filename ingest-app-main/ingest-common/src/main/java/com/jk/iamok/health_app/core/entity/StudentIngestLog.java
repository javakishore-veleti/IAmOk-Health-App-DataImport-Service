package com.jk.iamok.health_app.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "student_ingest_log")
public class StudentIngestLog extends AbstractEntity {

	@Column(name = "source_file_path", nullable = false)
	private String sourceFilePath;

	@Column(name = "total_records", nullable = false)
	private int totalRecords;

	@Column(name = "success_count")
	private int successCount;

	@Column(name = "failure_count")
	private int failureCount;

	@Column(name = "ingest_status", nullable = false)
	private String ingestStatus;

	@Column(name = "error_summary")
	private String errorSummary;

	// Getters and setters
}
