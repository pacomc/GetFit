package com.project.getfit.ui.inicio;

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
                .setOAuthConsumerKey("PEGAR LA CLAVE OBTENIDA")
                .setOAuthConsumerSecret("PEGAR LA CLAVE OBTENIDA")
                .setOAuthAccessToken("PEGAR LA CLAVE OBTENIDA")
                .setOAuthAccessTokenSecret("PEGAR LA CLAVE OBTENIDA");

        return cb;
    }

    public void getTimelineAsync(TwitterListener listener) {
        AsyncTwitterFactory factory = new AsyncTwitterFactory((getConfiguration().build()));
        AsyncTwitter asyncTwitter = factory.getInstance();
        asyncTwitter.addListener(listener);
        asyncTwitter.getHomeTimeline();
    }