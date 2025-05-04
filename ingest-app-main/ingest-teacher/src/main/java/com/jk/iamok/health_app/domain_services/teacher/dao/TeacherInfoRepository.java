package com.jk.iamok.health_app.domain_services.teacher.dao;

import com.jk.iamok.health_app.core.entity.TeacherInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherInfoRepository extends JpaRepository<TeacherInfo, String> {

}