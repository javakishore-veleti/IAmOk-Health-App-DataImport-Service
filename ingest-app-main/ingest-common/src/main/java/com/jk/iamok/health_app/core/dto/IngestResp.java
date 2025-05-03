package com.jk.iamok.health_app.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Response returned after initiating a batch ingestion job.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class IngestResp {

	private String ingestLogId;
	private String status;
	private String message;
}
