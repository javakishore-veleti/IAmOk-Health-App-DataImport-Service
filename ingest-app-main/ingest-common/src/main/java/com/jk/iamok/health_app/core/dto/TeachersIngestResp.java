package com.jk.iamok.health_app.core.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Response for teacher batch ingestion requests. Extends the generic IngestResp
 * to allow domain-specific additions later.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class TeachersIngestResp extends IngestResp {
	// Reserved for teacher-specific fields in future, e.g.:
	// private int numberOfTeachingAssignmentsProcessed;
}
