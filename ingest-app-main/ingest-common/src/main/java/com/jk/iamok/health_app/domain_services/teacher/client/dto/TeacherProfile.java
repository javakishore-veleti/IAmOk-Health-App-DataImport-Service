package com.jk.iamok.health_app.domain_services.teacher.client.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class TeacherProfile {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate joiningDate;
	private String employmentStatus;
}