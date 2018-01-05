package net.anotheria.moskito.extensions.slackbot;

import org.configureme.annotations.ConfigureMe;

@ConfigureMe(allfields = true)
public class SlackBotConfig {

    private String botToken;

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

}