package org.example;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRouterConfiguration {

    @Bean
    RoutesBuilder myRouter() {
        return new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("{{route.start}}").routeId("test").log("message ${body}").to("{{route.end}}");
            }

        };
    }

    private static JmsComponent createArtemisComponent() {
        // Sets up the Artemis core protocol connection factory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        JmsConfiguration configuration = new JmsConfiguration();
        configuration.setConnectionFactory(connectionFactory);

        return new JmsComponent(configuration);
    }
}