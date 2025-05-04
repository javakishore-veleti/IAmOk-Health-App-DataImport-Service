-- noinspection SqlDialectInspectionForFile

-- Create teaching_history table in the active schema (e.g., iam_ok_health)

CREATE TABLE IF NOT EXISTS teacher_info (
    id BIGSERIAL PRIMARY KEY,
    teacher_id VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    joining_date DATE NOT NULL,
    employment_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE teaching_history (
    id BIGINT NOT NULL PRIMARY KEY,
    course_id VARCHAR(255) ,
    course_name VARCHAR(255) ,
    course_start_year INT ,
    course_end_year INT ,
    teaching_hours INT ,

    teacher_info_id BIGINT NOT NULL,
    
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(100),
    updated_by VARCHAR(100),
    version INT NOT NULL DEFAULT 1,

    CONSTRAINT fk_teacher_info FOREIGN KEY (teacher_info_id)
        REFERENCES teacher_info (id)
        ON DELETE CASCADE
);