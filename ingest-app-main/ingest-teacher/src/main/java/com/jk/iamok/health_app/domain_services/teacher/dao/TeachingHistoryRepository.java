package com.jk.iamok.health_app.domain_services.teacher.dao;

import com.jk.iamok.health_app.core.entity.TeachingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingHistoryRepository extends JpaRepository<TeachingHistory, Long> {
	// You can add custom query methods here later if needed
}