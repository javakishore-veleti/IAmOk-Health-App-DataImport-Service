package com.jk.iamok.health_app.domain_services.teacher.client;

import com.jk.iamok.health_app.domain_services.teacher.client.dto.TeacherTeachingHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teachingServiceClient", url = "${external.teaching-service.url}")
public interface TeachingServiceClient {

	@GetMapping("/api/teaching-summary/{teacherId}")
	TeacherTeachingHistory getTeachingSummary(@PathVariable("teacherId") String teacherId);
}