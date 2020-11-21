package org.example;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@SpringBootTest
@AutoConfigureEmbeddedDatabase
@ActiveProfiles({"test", "embedded"})
public class DatabaseTest {

    @Autowired
    JdbcTemplate template;

    @Test
    public void test() throws SQLException {
        Map<String, Object> person = template.queryForMap("select * from person");
        assertThat(person).isNotNull();
        assertThat(person).contains(entry("first_name", "Tester"), entry("surname", "Testing"));
    }
}
