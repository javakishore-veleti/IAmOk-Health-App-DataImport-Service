package com.jk.iamok.health_app.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IngestLogIdGenerator {

    public static String generateIngestLogId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        String hostname = "unknown-host";

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            // Log this if necessary
        }

        return timestamp + "-" + hostname;
    }
}
