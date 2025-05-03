package com.jk.iamok.health_app.ingest.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Custom Flyway configuration for logging or overriding migration strategy.
 */
@Configuration
public class FlywayConfig {

	private static final Logger logger = LoggerFactory.getLogger(FlywayConfig.class);

	/**
	 * Customize Flyway behavior. This is useful to log or conditionally apply
	 * migrations.
	 */
	@Bean
	public FlywayMigrationStrategy flywayMigrationStrategy() {
		return flyway -> {
			logger.info("🔄 Running Flyway migrations for schema: {}",
					String.join(",", flyway.getConfiguration().getSchemas()));
			flyway.migrate();
		};
	}
}
