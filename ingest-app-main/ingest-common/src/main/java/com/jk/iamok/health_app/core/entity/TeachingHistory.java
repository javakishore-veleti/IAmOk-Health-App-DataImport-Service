package com.jk.iamok.health_app.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "teaching_history")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TeachingHistory extends AbstractEntity {

	@Column(name = "course_id", nullable = true)
	private String courseId;

	@Column(name = "course_name", nullable = true)
	private String courseName;

	@Column(name = "course_start_year", nullable = true)
	private int courseStartYear;

	@Column(name = "course_end_year", nullable = true)
	private int courseEndYear;

	@Column(name = "teaching_hours", nullable = true)
	private Integer teachingHours;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_info_id", nullable = true)
	private TeacherInfo teacherInfo;
}
