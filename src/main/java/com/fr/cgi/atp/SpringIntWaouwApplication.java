package com.fr.cgi.atp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringIntWaouwApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntWaouwApplication.class, args);

        MessageChannel inputChannel = context.getBean("input", MessageChannel.class);
        for (int i = 0; i < 5; i++) {
            inputChannel.send(new GenericMessage<Integer>(i)); // send message
        }
        context.close(); // shutdown
    }

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "input", outputChannel = "output")
    public Message<Integer> printPayload(Message<Integer> message) {
        log.debug("Payload : {}", message.getPayload());
        return message;
    }

    @Bean
    public MessageChannel output() {
        return new QueueChannel();
    }

}
