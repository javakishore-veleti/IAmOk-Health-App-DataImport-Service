package com.jk.iamok.health_app.ingest.api.controller;

import com.jk.iamok.health_app.core.dto.StudentsIngestReq;
import com.jk.iamok.health_app.core.dto.StudentsIngestResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Student Ingestion API", description = "Trigger batch ingestion of student data")
@RestController
@RequestMapping("/iamok/health_app/v1/students")
public class StudentIngestController {

	private static final Logger logger = LoggerFactory.getLogger(StudentIngestController.class);

	@Operation(summary = "Start student batch ingestion job", description = "Triggers asynchronous batch job to ingest student data from the provided source")
	@PostMapping("/batch-ingest")
	public ResponseEntity<StudentsIngestResp> ingestStudentData(@Valid @RequestBody StudentsIngestReq request) {
		String ingestLogId = UUID.randomUUID().toString();

		logger.info("Received student ingest request: fileUri={}, storageProvider={}, ingestLogId={}",
				request.getFileUri(), request.getStorageProviderType(), ingestLogId);

		StudentsIngestResp response = StudentsIngestResp.builder().message("Student ingestion job accepted")
				.ingestLogId(ingestLogId).status("accepted").build();

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
