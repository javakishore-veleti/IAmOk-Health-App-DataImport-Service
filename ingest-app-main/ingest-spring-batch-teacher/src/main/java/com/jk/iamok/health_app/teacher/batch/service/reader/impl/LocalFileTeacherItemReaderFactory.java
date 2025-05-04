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
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component("localFileTeacherItemReaderFactory")
public class LocalFileTeacherItemReaderFactory implements TeacherStorageItemReaderFactory {

    private static final Logger log = LoggerFactory.getLogger(LocalFileTeacherItemReaderFactory.class);

    @Override
    public FlatFileItemReader<TeacherCsvRow> createReader(TeachersIngestReqCtx ctx) {
        String filePath = ctx.getTeachersIngestReq().getFileUri();
        Path resolvedPath = Paths.get(filePath);

        log.info("Creating FlatFileItemReader for local file: {}", resolvedPath);

        return new FlatFileItemReaderBuilder<TeacherCsvRow>()
                .name("teacherCsvReader")
                .resource(new FileSystemResource(resolvedPath.toFile()))
                .linesToSkip(1) // assuming first line is header
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
}