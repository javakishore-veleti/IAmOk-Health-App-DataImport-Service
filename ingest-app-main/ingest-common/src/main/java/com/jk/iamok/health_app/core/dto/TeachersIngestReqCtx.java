package com.jk.iamok.health_app.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeachersIngestReqCtx extends IngestReqCtx {

    private TeachersIngestReq teachersIngestReq;
}
