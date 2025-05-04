package com.jk.iamok.health_app.teacher.batch.service;

import com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo;
import com.jk.iamok.health_app.core.entity.TeachingHistory;
import com.jk.iamok.health_app.domain_services.teacher.dao.TeachingHistoryRepository;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class TeachingHistoryWriter implements ItemWriter<TeacherEnrichedInfo> {

	private final TeachingHistoryRepository repository;

	public TeachingHistoryWriter(TeachingHistoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public void write(Chunk<? extends TeacherEnrichedInfo> items) {
		List<TeachingHistory> allHistories = StreamSupport.stream(items.spliterator(), false)
				.flatMap(enriched -> enriched.getTeachingHistoryList().stream()).toList();

		repository.saveAllAndFlush(allHistories);
	}
}
