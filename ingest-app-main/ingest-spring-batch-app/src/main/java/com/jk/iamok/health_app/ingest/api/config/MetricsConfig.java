package com.jk.iamok.health_app.ingest.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.config.MeterFilter;

@Configuration
public class MetricsConfig {

	@Bean
	public MeterFilter fixTagMismatchForPrometheus() {
		return MeterFilter.denyNameStartsWith("spring.batch.job.active");
	}
}