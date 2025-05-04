package com.jk.iamok.health_app.domain_services.teacher.client;

import com.jk.iamok.health_app.domain_services.teacher.client.dto.TeacherProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "teacherProfileServiceClient", url = "${external.teacher-profile-service.url}")
public interface TeacherProfileServiceClient {

	@GetMapping("/api/teacher-profile")
	TeacherProfile getTeacherProfile(@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("email") String email);
}