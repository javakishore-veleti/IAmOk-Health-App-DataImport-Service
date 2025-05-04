package com.jk.iamok.health_app.teacher.batch.service.writer;

import java.util.ArrayList;
import java.util.List;

import com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.jk.iamok.health_app.core.entity.TeacherInfo;
import com.jk.iamok.health_app.domain_services.teacher.dao.TeacherInfoRepository;

@Component("teacherInfoWriter")
public class TeacherInfoWriter implements ItemWriter<TeacherEnrichedInfo> {

	private final TeacherInfoRepository repository;

	public TeacherInfoWriter(TeacherInfoRepository repository) {
		this.repository = repository;
	}

	@Override
	public void write(Chunk<? extends TeacherEnrichedInfo> chunk) {
		List<TeacherInfo> teacherInfos = new ArrayList<>();
        for (TeacherEnrichedInfo item : chunk.getItems()) {
            teacherInfos.add(item.getTeacherInfo());
        }

        repository.saveAllAndFlush(teacherInfos);
	}
}