package com.jk.iamok.health_app.teacher.batch.service;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.jk.iamok.health_app.core.dto.TeacherEnrichedInfo;
import com.jk.iamok.health_app.core.entity.TeacherInfo;
import com.jk.iamok.health_app.core.entity.TeachingHistory;
import com.jk.iamok.health_app.domain_services.teacher.client.TeacherProfileServiceClient;
import com.jk.iamok.health_app.domain_services.teacher.client.TeachingServiceClient;
import com.jk.iamok.health_app.domain_services.teacher.client.dto.TeacherProfile;
import com.jk.iamok.health_app.domain_services.teacher.client.dto.TeacherTeachingHistory;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;

@Component
public class TeacherDataEnrichmentProcessor implements ItemProcessor<TeacherCsvRow, TeacherEnrichedInfo> {

	private final TeacherProfileServiceClient teacherProfileClient;
	private final TeachingServiceClient teachingServiceClient;

	public TeacherDataEnrichmentProcessor(TeacherProfileServiceClient profileClient,
			TeachingServiceClient teachingClient) {
		this.teacherProfileClient = profileClient;
		this.teachingServiceClient = teachingClient;
	}

	@Override
	public TeacherEnrichedInfo process(TeacherCsvRow row) throws Exception {
		TeacherProfile profile = teacherProfileClient.getTeacherProfile(row.getFirstName(), row.getLastName(),
				row.getEmail());
		TeacherTeachingHistory history = teachingServiceClient.getTeachingSummary(profile.getId());

		// Transform to TeacherInfo
		return mapToTeacherInfo(profile, history);
	}

	/**
	 * Utility method to map external API responses to TeacherInfo and
	 * TeachingHistory entities.
	 */
	private TeacherEnrichedInfo mapToTeacherInfo(TeacherProfile profile, TeacherTeachingHistory history) {
		// Convert profile to TeacherInfo
		TeacherInfo teacher = TeacherInfo.builder().firstName(profile.getFirstName()).lastName(profile.getLastName())
				.email(profile.getEmail()).joiningDate(profile.getJoiningDate())
				.employmentStatus(profile.getEmploymentStatus()).teacherId(profile.getId()).build();

		// Convert list of DTOs to TeachingHistory entities
		List<TeachingHistory> historyEntities = history.getTeachingHistory().stream()
				.map(dto -> TeachingHistory.builder().courseId(dto.getCourseId()).courseName(dto.getCourseName())
						.courseStartYear(dto.getCourseStartYear()).courseEndYear(dto.getCourseEndYear())
						.teachingHours(dto.getTeachingHours()).teacherInfo(teacher) // Set the owning side of the
																					// relationship
						.build())
				.toList();

		// Attach to wrapper
		return TeacherEnrichedInfo.builder().teacherInfo(teacher).teachingHistoryList(historyEntities).build();
	}
}