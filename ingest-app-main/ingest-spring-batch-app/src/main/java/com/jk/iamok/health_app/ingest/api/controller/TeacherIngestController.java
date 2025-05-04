package com.jk.iamok.health_app.ingest.api.controller;

import com.jk.iamok.health_app.core.dto.TeachersIngestReq;
import com.jk.iamok.health_app.core.dto.TeachersIngestResp;
import com.jk.iamok.health_app.core.service.TeacherIngestService;

import com.jk.iamok.health_app.util.IngestLogIdGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Teacher Ingestion API", description = "Trigger batch ingestion of teacher data")
@RestController
@RequestMapping("/iamok/health_app/v1/teachers")
public class TeacherIngestController {

	private static final Logger logger = LoggerFactory.getLogger(TeacherIngestController.class);

	private final TeacherIngestService teacherIngestService;

	public TeacherIngestController(TeacherIngestService teacherIngestService) {
		this.teacherIngestService = teacherIngestService;
	}

	@Operation(summary = "Start teacher batch ingestion job", description = "Triggers asynchronous batch job to ingest teacher data from the provided source")
	@PostMapping("/batch-ingest")
	public ResponseEntity<TeachersIngestResp> ingestTeacherData(@Valid @RequestBody TeachersIngestReq request) throws Exception {
		String ingestLogId = IngestLogIdGenerator.generateIngestLogId();

		logger.info("Received teacher ingest request: fileUri={}, storageProvider={}, ingestLogId={}",
				request.getFileUri(), request.getStorageProviderType(), ingestLogId);

		// Call service interface (will be backed by TeacherIngestServiceImpl)
		teacherIngestService.ingestTeachers(request, ingestLogId);

		TeachersIngestResp response = TeachersIngestResp.builder().message("Teacher ingestion job accepted")
				.ingestLogId(ingestLogId).status("accepted").build();

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
