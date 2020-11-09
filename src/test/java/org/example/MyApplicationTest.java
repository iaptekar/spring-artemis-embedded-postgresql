package org.example;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@CamelSpringBootTest
@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@TestPropertySource(properties = {"spring.flyway.enabled=false"})
//@MockEndpoints("direct:end")
@UseAdviceWith
public class MyApplicationTest {

    @Autowired
    private ProducerTemplate template;

    @EndpointInject("mock:direct:end")
    private MockEndpoint mock;

    @Autowired
    private CamelContext context;

    @BeforeEach
    public void setup() throws Exception {
        AdviceWithRouteBuilder.adviceWith(context, "test", route -> {
            route.interceptSendToEndpoint("{{route.end}}")
                    .skipSendToOriginalEndpoint()
                    .to("mock:direct:end");
        });
        context.start();
    }

    @Test
    public void testReceive() throws Exception {
        mock.expectedBodiesReceived("Hello");
        template.sendBody("{{route.start}}", "Hello");
        mock.assertIsSatisfied();
    }

}