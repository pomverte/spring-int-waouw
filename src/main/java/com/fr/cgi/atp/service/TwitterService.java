///////////////////////////////////////////////////////////////////////////////
//
// Ce fichier fait partie du projet Synergie - (C) Copyright 2014
// Tous droits réservés à L'Agence de Services et de Paiement (ASP).
//
// Tout ou partie de Synergie ne peut être copié et/ou distribué
// sans l'accord formel de l'ASP.
//
///////////////////////////////////////////////////////////////////////////////
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
