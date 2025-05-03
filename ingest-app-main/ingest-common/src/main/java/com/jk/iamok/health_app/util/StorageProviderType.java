package com.jk.iamok.health_app.util;

public enum StorageProviderType {
	LOCAL, S3, GCS, AZURE;

	public static StorageProviderType fromString(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Storage provider type must not be null");
		}
		try {
			return StorageProviderType.valueOf(value.trim().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Unsupported storage provider type: " + value);
		}
	}
}