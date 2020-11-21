package org.example;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArtemisConsumer {

    Object lock = new Object();

    String message;

    @JmsListener(destination = "${jms.queue.destination}")
    public void receive(String msg) {
        System.out.println("Got Message: " + msg);
        synchronized (lock) {
            this.message = msg;
            lock.notify();
        }
    }

    String getMessage() throws InterruptedException {
        synchronized (lock) {
            if (message == null) {
                lock.wait(10_000);
            }
            return message;
        }
    }
}