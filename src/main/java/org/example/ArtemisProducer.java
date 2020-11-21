package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@Component
public class ArtemisProducer {
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Value("${jms.queue.destination}")
    String destinationQueue;

    public void send(String msg) {
        jmsTemplate.convertAndSend(destinationQueue, msg);
    }
}