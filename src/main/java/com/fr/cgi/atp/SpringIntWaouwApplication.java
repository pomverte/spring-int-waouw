package com.fr.cgi.atp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.social.twitter.api.Tweet;

import com.fr.cgi.atp.service.TwitterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringIntWaouwApplication {

    @Autowired
    private TwitterService twitterService;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntWaouwApplication.class, args);
    }

    // twitter inbound channel adapter with a poller
    @InboundChannelAdapter(value = "input", poller = @Poller(fixedRate = "500", maxMessagesPerPoll = "3") )
    public List<Tweet> twitterAdapter() {
        log.debug("SpringIntWaouwApplication.twitterAdapter(TwitterService)");
        return this.twitterService.getUserTimeline("vietnem");
    }

    @ServiceActivator(inputChannel = "input", outputChannel = "output")
    public Message<Tweet> printPayload(Message<Tweet> message) {
        Tweet payload = message.getPayload();
        log.debug("Tweet : {} from {}", payload.getText(), payload.getFromUser());
        return message;
    }

    @Bean
    public MessageChannel output() {
        return new QueueChannel();
    }

}
