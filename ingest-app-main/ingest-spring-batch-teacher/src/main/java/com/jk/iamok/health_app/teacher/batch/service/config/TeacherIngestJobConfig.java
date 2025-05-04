package com.jk.iamok.health_app.teacher.batch.service.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.jk.iamok.health_app.core.entity.TeacherInfo;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;

@Configuration
public class TeacherIngestJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public TeacherIngestJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean(name = "teacherIngestJob")
	public Job teacherIngestJob(Step teacherIngestStep) {
		return new JobBuilder("teacherIngestJob", jobRepository).start(teacherIngestStep).build();
	}

	@Bean(name = "teacherIngestStep")
	public Step teacherIngestStep(ItemReader<TeacherCsvRow> reader, ItemProcessor<TeacherCsvRow, TeacherInfo> processor,
			ItemWriter<TeacherInfo> writer) {

		return new StepBuilder("teacherIngestStep", jobRepository)
				.<TeacherCsvRow, TeacherInfo>chunk(100, transactionManager).reader(reader).processor(processor)
				.writer(writer).build();
	}
}
