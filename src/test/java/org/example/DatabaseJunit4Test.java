package org.example;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    //@Rule
    //public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();

    @Autowired
    ArtemisProducer producer;

    @Autowired
    ArtemisConsumer consumer;

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

    @Value("${my.schema}")
    public String schema1;
    @Value("${spring.flyway.default-schema}")
    public String schema2;
    @Value("${spring.flyway.schemas}")
    public String schema3;
    @Value("${zonky.test.database.postgres.client.properties.currentSchema}")
    public String schema4;

    @Before
    public void setup() {
        System.out.println(schema1);
        System.out.println(schema2);
        System.out.println(schema3);
        System.out.println(schema4);
    }

    @Test
    public void test() throws SQLException {
        Map<String, Object> person = template.queryForMap("select * from person");
        assertThat(person).isNotNull();
        assertThat(person).contains(entry("first_name", "Tester"), entry("surname", "Testing"));
    }
}
