package com.jk.iamok.health_app.domain_services.teacher.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TeachingHistory {
	private String courseId;
	private String courseName;
	private int courseStartYear;
	private int courseEndYear;
	private int teachingHours;
}