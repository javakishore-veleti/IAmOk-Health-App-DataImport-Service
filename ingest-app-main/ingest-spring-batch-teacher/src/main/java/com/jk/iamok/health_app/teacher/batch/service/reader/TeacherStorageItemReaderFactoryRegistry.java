package com.jk.iamok.health_app.teacher.batch.service.reader;

import com.jk.iamok.health_app.constants.StorageProviderType;
import com.jk.iamok.health_app.teacher.batch.service.reader.impl.LocalFileTeacherItemReaderFactory;
import com.jk.iamok.health_app.teacher.batch.service.reader.impl.S3TeacherItemReaderFactory;
import com.jk.iamok.health_app.core.dto.TeachersIngestReqCtx;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TeacherStorageItemReaderFactoryRegistry {

    private final Map<String, TeacherStorageItemReaderFactory> factoryMap;

    public TeacherStorageItemReaderFactoryRegistry(
            LocalFileTeacherItemReaderFactory localFactory,
            S3TeacherItemReaderFactory s3Factory
            // add azureFactory, gcsFactory if needed
    ) {
        this.factoryMap = Map.of(
                StorageProviderType.LOCAL.name().toLowerCase(), localFactory,
                StorageProviderType.AWS_S3.name(), s3Factory
                // , "azure", azureFactory, "gcs", gcsFactory
        );
    }

    public ItemReader<TeacherCsvRow> getReader(TeachersIngestReqCtx ctx) {
        String type = ctx.getTeachersIngestReq().getStorageProviderType().name().toLowerCase();
        TeacherStorageItemReaderFactory factory = factoryMap.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported storageProviderType: " + type);
        }
        return factory.createReader(ctx);
    }
}