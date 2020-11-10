package org.example;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.apache.activemq.artemis.junit.EmbeddedActiveMQResource;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureEmbeddedDatabase
@ActiveProfiles({"test", "embedded"})
public class DatabaseJunit4Test {

    @Rule
    public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();

//    @TestConfiguration
//    public class EmbeddedPostgresConfiguration {
//
//        @Bean
//        public Consumer<EmbeddedPostgres.Builder> embeddedPostgresCustomizer() {
//            return builder -> builder.setPGStartupWait(Duration.ofSeconds(60L)).setCleanDataDirectory(true);
//            // .setConnectConfig("currentSchema", "test");
//        }
//    }

    @Autowired
    JdbcTemplate template;

    @Test
    public void test() throws SQLException {
        Map<String, Object> person = template.queryForMap("select * from person");
        assertThat(person).isNotNull();
        assertThat(person).contains(entry("first_name", "Tester"), entry("surname", "Testing"));
    }
}
