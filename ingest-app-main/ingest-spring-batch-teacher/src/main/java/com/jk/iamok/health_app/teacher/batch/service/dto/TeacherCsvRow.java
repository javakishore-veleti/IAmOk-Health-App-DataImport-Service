package com.jk.iamok.health_app.teacher.batch.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherCsvRow {
	private String firstName;
	private String lastName;
	private String email;
}