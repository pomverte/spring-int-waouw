package com.fr.cgi.atp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
public class SpringIntWaouwApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntWaouwApplication.class, args);

        MessageChannel inputChannel = context.getBean("splitted", MessageChannel.class);
        for (int i = 0; i < 5; i++) {
            inputChannel.send(new GenericMessage<Integer>(i));
        }
        context.close();
    }

    @Router(inputChannel = "splitted")
    public String oddOrEven(Integer number) {
        return number % 2 == 0 ? "even" : "odd";
    }

    @Transformer(inputChannel = "odd", outputChannel = "exit")
    public Message<Integer> addCommitter(Message<Integer> message) {
        return MessageBuilder.fromMessage(message).setHeader("comitter", "Hong Viet").build();
    }

    @Filter(inputChannel = "even", outputChannel = "exit")
    public boolean twoOnly(Integer number) {
        return number.equals(2);
    }

    @ServiceActivator(inputChannel = "exit", outputChannel = "nullChannel")
    public Message<?> logger(Message<Integer> message) {
        System.out.println(String.format("Coucou je suis le message %s envoyé par %s", message.getPayload(), message
                .getHeaders().get("comitter")));
        return message;
    }

}
