package com.jk.iamok.health_app.core.dto;

import com.jk.iamok.health_app.core.entity.TeacherInfo;
import com.jk.iamok.health_app.core.entity.TeachingHistory;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherEnrichedInfo {
	private TeacherInfo teacherInfo;
	private List<TeachingHistory> teachingHistoryList;
}
