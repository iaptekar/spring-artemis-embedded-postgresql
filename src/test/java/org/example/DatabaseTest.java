package org.example;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@SpringBootTest
@AutoConfigureEmbeddedDatabase
public class DatabaseTest {

    @TestConfiguration
    public class EmbeddedPostgresConfiguration {

        @Bean
        public Consumer<EmbeddedPostgres.Builder> embeddedPostgresCustomizer() {
            return builder -> builder.setPGStartupWait(Duration.ofSeconds(60L));
            // .setConnectConfig("currentSchema", "test");
        }
    }

    @Autowired
    JdbcTemplate template;

    @Test
    public void test() throws SQLException {
        Map<String, Object> person = template.queryForMap("select * from person");
        assertThat(person).isNotNull();
        assertThat(person).contains(entry("first_name", "Tester"), entry("surname", "Testing"));
    }
}
