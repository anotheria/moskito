package net.anotheria.moskito.extensions.notificationproviders;

/**
 * @author Yuriy Koval'.
 */
public enum MailerConfigKey {

    THRESHOLD_ALERT_HTML_PATH("thresholdMailHTMLTplUrl"),
    THRESHOLD_ALERT_TEXT_PATH("thresholdMailTextTplUrl"),
    RECIPIENTS("recipients");

    private String key;

    MailerConfigKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
