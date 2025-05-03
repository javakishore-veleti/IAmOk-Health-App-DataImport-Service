package com.jk.iamok.health_app.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IngestReq {
	@NotBlank
	private String fileUri;

	@NotBlank
	private String storageProviderType; // e.g., local, s3, gcs, azure
}