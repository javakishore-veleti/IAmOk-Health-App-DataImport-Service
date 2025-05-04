package com.jk.iamok.health_app.teacher.batch.service.config;

import com.jk.iamok.health_app.core.dto.TeachersIngestReqCtx;
import com.jk.iamok.health_app.teacher.batch.service.reader.TeacherStorageItemReaderFactoryRegistry;
import com.jk.iamok.health_app.util.AppUtil;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TeacherIngestJobConfig {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public TeacherIngestJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean(name = "partitionedTeacherIngestJob")
	public Job teacherIngestJob(Step teacherIngestStep) {
		return new JobBuilder("partitionedTeacherIngestJob", jobRepository).start(teacherIngestStep).build();
	}

	@Bean(name = "teacherEnrichedCompositeWriter")
	public CompositeItemWriter<TeacherEnrichedInfo> compositeWriter(
			ItemWriter<TeacherEnrichedInfo> teacherInfoWriter,
			ItemWriter<TeacherEnrichedInfo> teachingHistoryWriter,
			ItemWriter<TeacherEnrichedInfo> teacherJsonFileWriter
	) {
		CompositeItemWriter<com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo> compositeWriter = new CompositeItemWriter<>();
		List<ItemWriter<? super TeacherEnrichedInfo>> writers = Arrays.asList(
				teacherInfoWriter,
				teachingHistoryWriter,
				teacherJsonFileWriter
		);
		compositeWriter.setDelegates(writers);
		return compositeWriter;
	}

	@Bean(name = "teacherCsvReader")
	@StepScope
	public ItemReader<TeacherCsvRow> teacherCsvReader(
			@Value("#{jobParameters['teachersIngestReqCtx']}") String teachersIngestReqCtxJson,
			TeacherStorageItemReaderFactoryRegistry registry
	) throws Exception {
		TeachersIngestReqCtx context = AppUtil.getObjectMapper().readValue(teachersIngestReqCtxJson, TeachersIngestReqCtx.class);
		return registry.getReader(context);
	}

	@Bean(name = "teacherIngestStep")
	public Step teacherIngestStep(ItemReader<TeacherCsvRow> reader,
			ItemProcessor<TeacherCsvRow, TeacherEnrichedInfo> processor,
								  @Qualifier("teacherEnrichedCompositeWriter") ItemWriter<TeacherEnrichedInfo> compositeWriter) {

		return new StepBuilder("teacherIngestStep", jobRepository)
				.<TeacherCsvRow, TeacherEnrichedInfo>chunk(100, transactionManager).reader(reader).processor(processor)
				.writer(compositeWriter).build();
	}
}
