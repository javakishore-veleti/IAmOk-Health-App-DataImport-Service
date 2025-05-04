package com.jk.iamok.health_app.teacher.batch.service.reader.impl;

import com.jk.iamok.health_app.core.dto.TeachersIngestReqCtx;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;
import com.jk.iamok.health_app.teacher.batch.service.reader.TeacherStorageItemReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@SuppressWarnings("DataFlowIssue")
@Component("s3TeacherItemReaderFactory")
public class S3TeacherItemReaderFactory implements TeacherStorageItemReaderFactory {

    private static final Logger log = LoggerFactory.getLogger(S3TeacherItemReaderFactory.class);
    //private final AmazonS3 amazonS3;

    // public S3TeacherItemReaderFactory(AmazonS3 amazonS3) {
        //this.amazonS3 = amazonS3;
    //}


    @SuppressWarnings("ConstantValue")
    @Override
    public FlatFileItemReader<TeacherCsvRow> createReader(TeachersIngestReqCtx ctx) {
        String s3Uri = ctx.getTeachersIngestReq().getFileUri(); // format: s3://bucket/path/to/file.csv
        String bucket = extractBucketName(s3Uri);
        String key = extractKey(s3Uri);

        log.info("Fetching file from S3: bucket={}, key={}", bucket, key);
        //S3Object s3Object = amazonS3.getObject(bucket, key);
        //InputStream inputStream = s3Object.getObjectContent();
        InputStream inputStream = null;

        return new FlatFileItemReaderBuilder<TeacherCsvRow>()
                .name("s3TeacherCsvReader")
                .resource(new InputStreamResource(inputStream))
                .linesToSkip(1)
                .lineMapper(new DefaultLineMapper<>() {{
                    setLineTokenizer(new DelimitedLineTokenizer() {{
                        setNames("firstName", "lastName", "email");
                    }});
                    setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                        setTargetType(TeacherCsvRow.class);
                    }});
                }})
                .build();
    }

    private String extractBucketName(String s3Uri) {
        return s3Uri.replace("s3://", "").split("/")[0];
    }

    private String extractKey(String s3Uri) {
        return s3Uri.replace("s3://", "").substring(s3Uri.indexOf("/") + 1);
    }
}
