package org.example;

import org.apache.activemq.artemis.junit.EmbeddedActiveMQResource;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@TestPropertySource(properties = "spring.flyway.enabled=false")
public class ArtemisTest {

    @Rule
    public EmbeddedActiveMQResource resource = new EmbeddedActiveMQResource();

    @Autowired
    ArtemisProducer producer;

    @Autowired
    ArtemisConsumer consumer;

    @Test
    public void test() throws Exception {
        producer.send("hello world");
        String message = consumer.getMessage();
        assertThat(message).isNotNull();
        assertThat(message).isEqualTo("hello world");
    }
}
