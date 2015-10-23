package com.fr.cgi.atp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

@Service
public class TwitterService {

    @Autowired
    private Twitter twitter;

    public List<Tweet> getUserTimeline(String twitterUser) {
        TimelineOperations timelineOps = this.twitter.timelineOperations();
        return timelineOps.getUserTimeline("@" + twitterUser);
    }

}
