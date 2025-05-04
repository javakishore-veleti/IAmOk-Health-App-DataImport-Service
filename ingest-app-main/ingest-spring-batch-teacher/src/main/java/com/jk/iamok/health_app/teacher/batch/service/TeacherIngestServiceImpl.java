package com.jk.iamok.health_app.teacher.batch.service;

import com.jk.iamok.health_app.core.dto.TeachersIngestReq;
import com.jk.iamok.health_app.core.service.TeacherIngestService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TeacherIngestServiceImpl implements TeacherIngestService {

	private static final Logger logger = LoggerFactory.getLogger(TeacherIngestServiceImpl.class);

	private final JobLauncher jobLauncher;
	private final Job partitionedTeacherIngestJob;

	public TeacherIngestServiceImpl(JobLauncher jobLauncher,
			@Qualifier("partitionedTeacherIngestJob") Job partitionedTeacherIngestJob) {
		this.jobLauncher = jobLauncher;
		this.partitionedTeacherIngestJob = partitionedTeacherIngestJob;
	}

	@Override
	public void ingestTeachers(TeachersIngestReq request, String ingestLogId) {
		JobParameters jobParameters = new JobParametersBuilder().addString("fileUri", request.getFileUri())
				.addString("storageProviderType", request.getStorageProviderType())
				.addString("ingestLogId", ingestLogId).addLong("timestamp", System.currentTimeMillis())
				.toJobParameters();

		try {
			logger.info("Starting teacher ingestion job. IngestLogId={}, FileUri={}, Provider={}", ingestLogId,
					request.getFileUri(), request.getStorageProviderType());

			jobLauncher.run(partitionedTeacherIngestJob, jobParameters);
		} catch (Exception e) {
			logger.error("Failed to start teacher ingestion job. IngestLogId={}", ingestLogId, e);
			throw new RuntimeException("Teacher ingestion job failed to start", e);
		}
	}
}
