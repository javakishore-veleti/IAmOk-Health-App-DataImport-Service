package com.jk.iamok.health_app.ingest.api.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TeacherIngestJobConfig {

	@Bean
	public JobBuilder jobBuilder(JobRepository jobRepository) {
		return new JobBuilder("teacherIngestJob", jobRepository);
	}

	@Bean
	public Step teacherIngestStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("teacherIngestStep", jobRepository).tasklet((contribution, chunkContext) -> {
			System.out.println("Stub teacher step executed.");
			return RepeatStatus.FINISHED;
		}, transactionManager).build();
	}

	@Bean(name = "teacherIngestJob")
	public Job teacherIngestJob(JobBuilder jobBuilder, Step teacherIngestStep) {
		return jobBuilder.start(teacherIngestStep).build();
	}
}
