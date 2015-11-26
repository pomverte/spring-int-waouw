package com.fr.cgi.atp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class SpringIntWaouwApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntWaouwApplication.class, args);

        MessageChannel inputChannel = context.getBean("exit", MessageChannel.class);
        for (int i = 0; i < 5; i++) {
            inputChannel.send(new GenericMessage<Integer>(i)); // send message
        }
        context.close(); // shutdown
    }

    // implicite exit directChannel
    @ServiceActivator(inputChannel = "exit", outputChannel = "nullChannel")
    public Message<?> logger(Integer number) {
        System.out.println(String.format("Coucou je suis le message %s", number));
        return new GenericMessage<Integer>(number);
    }

}
