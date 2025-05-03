package com.jk.iamok.health_app.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Auditable base class for all JPA entities.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class AbstractEntity implements java.io.Serializable {

	@Id
	@Column(name = "id", nullable = false, updatable = false, unique = true)
	private String id;

	@Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;

	@Column(name = "created_by", nullable = true, updatable = false)
	private String createdBy;

	@Column(name = "updated_by", nullable = true)
	private String updatedBy;

	@Version
	@Column(name = "version", nullable = false)
	private Long version;

	@PrePersist
	protected void onCreate() {
		if (id == null) {
			this.id = UUID.randomUUID().toString();
		}
		this.createdAt = this.updatedAt = LocalDateTime.now();
		this.createdBy = getCurrentUsername(); // Placeholder
		this.updatedBy = this.createdBy;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = getCurrentUsername(); // Placeholder
	}

	protected String getCurrentUsername() {
		// Customize this to fetch from security context or request context
		return "system"; // Default fallback
	}

}
