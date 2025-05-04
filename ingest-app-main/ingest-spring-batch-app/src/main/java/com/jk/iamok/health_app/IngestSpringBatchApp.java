package com.jk.iamok.health_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients(basePackages = "com.jk.iamok.health_app.domain_services.teacher.client")
@SpringBootApplication
public class IngestSpringBatchApp {

	public static void main(String[] args) {
		SpringApplication.run(IngestSpringBatchApp.class, args);
	}

}