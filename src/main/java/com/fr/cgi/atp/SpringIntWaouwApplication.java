package com.fr.cgi.atp;

import java.util.Map.Entry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@IntegrationComponentScan // required for @MessagingGateway
@SpringBootApplication
public class SpringIntWaouwApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringIntWaouwApplication.class, args);

        StarGate stargate = context.getBean(StarGate.class);
        for (int i = 0; i < 5; i++) {
            stargate.chevron(i); // send message
        }
        context.close(); // shutdown
    }

    @MessagingGateway
    public interface StarGate {
        @Gateway(requestChannel="input")
        void chevron(Integer value);
    }

    @Bean
    public MessageChannel input() {
        return new PublishSubscribeChannel();
    }

    @ServiceActivator(inputChannel = "input", outputChannel = "output")
    public Message<Integer> printPayload(Message<Integer> message) {
        log.debug("Payload : {}", message.getPayload());
        return message;
    }

    @ServiceActivator(inputChannel = "input", outputChannel = "output")
    public Message<Integer> printHeaders(Message<Integer> message) {
        String headerPretty = "Header : ";
        for (Entry<String, Object> header : message.getHeaders().entrySet()) {
            headerPretty += String.format("[%s - %s]", header.getKey(), header.getValue());
        }
        log.debug(headerPretty);
        return message;
    }

    @Bean
    public MessageChannel output() {
        return new QueueChannel();
    }

    // output is a queue channel, so we need to poll msg from it
    @Router(inputChannel = "output", poller = @Poller(maxMessagesPerPoll = "2", fixedDelay = "1000") )
    public String oddOrEven(@Payload Integer payload) {
        return payload % 2 == 0 ? "exit" : "odd";
    }

    @Bean
    public MessageChannel odd() {
        return new QueueChannel();
    }

    @Transformer(inputChannel = "odd", outputChannel = "output", poller = @Poller(maxMessagesPerPoll = "2",
            fixedDelay = "1000"))
    public <T> Message<T> addCommitter(Message<T> message) {
        return MessageBuilder.fromMessage(message).setHeaderIfAbsent("commiter", "Hong Viet").build();
    }

    @Bean
    public MessageChannel exit() {
        return new QueueChannel();
    }

}
