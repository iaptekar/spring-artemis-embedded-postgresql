package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, ArtemisAutoConfiguration.class})
@TestPropertySource(properties = "spring.flyway.enabled=false")
@ActiveProfiles("test")
@Testcontainers
public class SolaceDockerTest {

    static Logger log = LoggerFactory.getLogger("testing");

    static String service = "primary";
    static String service1 = service + "_1";
    static String service2 = service + "_2";
    static int port = 55555;

//    @ClassRule
//    public static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("solace/solace-pubsub-standard:latest")
//            .withExposedPorts(5555);

    @Container
    public static DockerComposeContainer compose =
            new DockerComposeContainer(new File("src/test/resources/PubSubStandard_singleNode.yml"))
                    .withExposedService(service, 1, port)
                    //.withExposedService(service2, 8080)
                    .withLocalCompose(true);

    @Autowired
    ArtemisProducer producer;

    @Autowired
    ArtemisConsumer consumer;

    @DynamicPropertySource
    static void solaceProperties(DynamicPropertyRegistry registry) {

        registry.add("solace.jms.host", () -> compose.getServiceHost(service1, port) + ":" + compose.getServicePort(service1, port));

    }

    @Test
    public void test() throws Exception {
        producer.send("hello world");
        String message = consumer.getMessage();
        assertThat(message).isNotNull();
        assertThat(message).isEqualTo("hello world");
    }
}
