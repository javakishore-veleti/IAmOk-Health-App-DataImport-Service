package com.jk.iamok.health_app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CsvFilePartitioner implements Partitioner {

	private static final Logger logger = LoggerFactory.getLogger(CsvFilePartitioner.class);
	private final Path inputFolder;

	public CsvFilePartitioner(Path inputFolder) {
		this.inputFolder = inputFolder;
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		Map<String, ExecutionContext> partitions = new HashMap<>();

		try (Stream<Path> stream = Files.list(inputFolder)) {
			int partitionIndex = 0;

			for (Path filePath : stream.filter(Files::isRegularFile).toList()) {
				String partitionKey = "partition_" + partitionIndex;
				ExecutionContext context = new ExecutionContext();
				context.putString("filePath", filePath.toAbsolutePath().toString());

				partitions.put(partitionKey, context);
				logger.info("Partition [{}] assigned file: {}", partitionKey, filePath.getFileName());

				partitionIndex++;
			}

		} catch (IOException e) {
			logger.error("Error reading input folder for partitioning", e);
			throw new RuntimeException("Failed to partition files", e);
		}

		return partitions;
	}
}