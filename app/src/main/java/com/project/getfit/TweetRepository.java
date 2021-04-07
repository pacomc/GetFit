package com.project.getfit;


import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.TwitterListener;
import twitter4j.conf.ConfigurationBuilder;

public class TweetRepository {
    private static TweetRepository instance;

    private TweetRepository() {}

    public static TweetRepository getInstance() {
        if (instance == null) {
            instance = new TweetRepository();
        }
        return instance;
    }
    private ConfigurationBuilder getConfiguration() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("EuOER6WmxCNxyzHNVB983yDdw")
                .setOAuthConsumerSecret("F9JMQu5kSGole2ZqbDtFUvMVcUtsodyXE69oNhexm6P6A6zYMw")
                .setOAuthAccessToken("1374677001945120768-h7lELdEi6RdhfQzt1uemDzdwXzB7VR")
                .setOAuthAccessTokenSecret("zj2wG0wIS28p5uUFIPlHIPj6jFm6WdsSLuVBZVcW1jMG3");

        return cb;
    }

    public void getTimelineAsync(TwitterListener listener) {
        AsyncTwitterFactory factory = new AsyncTwitterFactory((getConfiguration().build()));
        AsyncTwitter asyncTwitter = factory.getInstance();
        asyncTwitter.addListener(listener);
        asyncTwitter.getHomeTimeline();
    }
}