package net.anotheria.moskito.extensions.notificationproviders;

/**
 * Notification provider config key enum.
 *
 * @author Yuriy Koval'.
 */
public enum NotificationProviderConfigKey {

    HTML_TEMPLATE_PATH("htmlTemplatePath"),
    TEXT_TEMPLATE_PATH("textTemplatePath"),
    RECIPIENTS("recipients");

    private String key;

    NotificationProviderConfigKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
