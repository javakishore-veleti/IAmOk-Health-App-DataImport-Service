package com.jk.iamok.health_app.core.service;

import com.jk.iamok.health_app.core.dto.TeachersIngestReq;

public interface TeacherIngestService {
	void ingestTeachers(TeachersIngestReq request, String ingestLogId) throws Exception;
}