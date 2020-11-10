package org.example;

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
@ActiveProfiles("embedded")
public class EmbeddedPostgresConfiguration {

    @Bean
    public Consumer<EmbeddedPostgres.Builder> embeddedPostgresCustomizer() {
        File dir = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        return builder -> builder
                .setOverrideWorkingDirectory(new File(dir, "work"))
                .setDataDirectory(new File(dir, "data"))
                .setPGStartupWait(Duration.ofSeconds(60L)).setCleanDataDirectory(true);
        // .setConnectConfig("currentSchema", "test");
    }
}
