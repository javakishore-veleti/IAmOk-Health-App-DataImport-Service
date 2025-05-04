package com.jk.iamok.health_app.teacher.batch.service;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.jk.iamok.health_app.core.entity.TeacherInfo;
import com.jk.iamok.health_app.domain_services.teacher.dao.TeacherInfoRepository;

@Component
public class TeacherInfoWriter implements ItemWriter<TeacherInfo> {

	private final TeacherInfoRepository repository;

	public TeacherInfoWriter(TeacherInfoRepository repository) {
		this.repository = repository;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(Chunk<? extends TeacherInfo> chunk) throws Exception {
		repository.saveAllAndFlush((List<TeacherInfo>) chunk.getItems());
	}
}