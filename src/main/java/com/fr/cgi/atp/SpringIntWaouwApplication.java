package com.fr.cgi.atp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@IntegrationComponentScan // required for @MessagingGateway
@SpringBootApplication
public class SpringIntWaouwApplication {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntWaouwApplication.class, args);

        StarGate stargate = context.getBean(StarGate.class);
        while (true) {
            stargate.chevron(0, 1, 2, 3, 4); // send only one message
            System.out.println("");
            Thread.sleep(5000);
        }
    }

    @MessagingGateway
    public interface StarGate {
        @Gateway(requestChannel = "mixed")
        void chevron(Integer... value);
    }

    @Splitter(inputChannel = "mixed", outputChannel = "splitted")
    public List<Integer> banana(Integer... payload) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (Integer current : payload) {
            result.add(current);
        }
        return result;
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
