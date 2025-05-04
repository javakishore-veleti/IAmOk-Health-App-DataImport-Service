package com.jk.iamok.health_app.domain_services.teacher.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TeacherTeachingHistory {
	private String teacherId;
	private List<TeachingHistory> teachingHistory;
}