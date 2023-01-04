package de.htwg.caduserservice.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.MonitoredResource;
import com.google.cloud.logging.LogEntry;
import com.google.cloud.logging.Logging;
import com.google.cloud.logging.LoggingOptions;
import com.google.cloud.logging.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Service
public class LoggerUtil {
    private static LoggingOptions cloudLoggingOptions;
    @Value("${spring.cloud.gcp.project-id}")
    private String googleProjectId;

    public static void log(String message) {
        //TODO severity
        LogEntry firstEntry = LogEntry.newBuilder(Payload.StringPayload.of(message))
                .setLogName("user-service")
                .setResource(MonitoredResource.newBuilder("global")
                        .addLabel("project_id", cloudLoggingOptions.getProjectId())
                        .build())
                .build();
        try (Logging logger = cloudLoggingOptions.getService()) {
            logger.write(Collections.singleton(firstEntry));
            System.out.println(message);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @PostConstruct
    private void init() throws IOException {
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("classpath:tf_service_account_key.json");
        assert serviceAccount != null;
        cloudLoggingOptions = LoggingOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId(googleProjectId)
                .build();
    }


}
