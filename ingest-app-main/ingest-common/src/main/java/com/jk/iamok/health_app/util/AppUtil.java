package com.jk.iamok.health_app.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }
}
