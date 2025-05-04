package com.jk.iamok.health_app.teacher.batch.service.reader;

import com.jk.iamok.health_app.core.dto.TeachersIngestReqCtx;
import com.jk.iamok.health_app.teacher.batch.service.dto.TeacherCsvRow;
import org.springframework.batch.item.ItemReader;

public interface TeacherStorageItemReaderFactory {

    /**
     * Creates an ItemReader to read TeacherCsvRow items based on ingestion context.
     *
     * @param ingestReqCtx enriched ingestion context containing file URI, provider type, and ingest log ID
     * @return a suitable ItemReader implementation
     */
    ItemReader<TeacherCsvRow> createReader(TeachersIngestReqCtx ingestReqCtx);
}
