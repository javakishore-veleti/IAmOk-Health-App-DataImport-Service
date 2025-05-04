package com.jk.iamok.health_app.teacher.batch.service.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
@StepScope
public class TeacherJsonFileWriter implements ItemWriter<TeacherEnrichedInfo> {

	private static final Logger logger = LoggerFactory.getLogger(TeacherJsonFileWriter.class);

	private final String ingestLogId;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final Path outputDir;

	public TeacherJsonFileWriter(@Value("#{jobParameters['ingestLogId']}") String ingestLogId) {
		this.ingestLogId = ingestLogId;
		this.outputDir = Paths.get("output", "teachers", ingestLogId);
	}

	@Override
	public void write(Chunk<? extends TeacherEnrichedInfo> items) throws IOException {
		logger.info(ingestLogId + " - Writing chunk of size: " + items.size());

		// Ensure directory exists
		Files.createDirectories(outputDir);

		// Generate unique file name per chunk
		String fileName = "teachers_chunk_" + UUID.randomUUID() + ".jsonl";
		Path filePath = outputDir.resolve(fileName);

		try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE)) {
			for (TeacherEnrichedInfo record : items) {
				writer.write(objectMapper.writeValueAsString(record));
				writer.newLine(); // NDJSON format (1 JSON object per line)
			}
		}
	}
}
