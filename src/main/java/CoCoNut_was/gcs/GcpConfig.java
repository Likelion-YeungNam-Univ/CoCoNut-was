package CoCoNut_was.gcs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class GcpConfig {

    @Value("${spring.cloud.gcp.credentials.location}")
    private Resource gcpCredentials;

    @Value("${spring.cloud.gcp.project-id}")
    private String projectId;

    @Bean
    public Storage storage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(gcpCredentials.getInputStream());
        return StorageOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
