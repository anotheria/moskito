package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.communication.data.HtmlMailMessage;
import net.anotheria.communication.service.MessagingService;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.util.IOUtils;
import net.anotheria.moskito.extensions.notificationtemplate.AlertThresholdTemplate;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This notification provider sends thresholds via email.
 *
 * @author lrosenberg
 * @since 23.10.12 15:48
 */
public class MailNotificationProvider implements NotificationProvider {

    /**
     * List of recipients.
     */
    private List<String> recipients;

    /**
     * Messaging service instance for smtp connection.
     */
    private MessagingService messagingService;

    /**
     * Html mail template string.
     */
    private String htmlTemplateString;

    /**
     * Plain text mail template string.
     */
    private String plainTextTemplateString;

    /**
     * Log.
     */
    private static Logger log = LoggerFactory.getLogger(MailNotificationProvider.class);


    public MailNotificationProvider() {
        recipients = new ArrayList<String>();
    }

    @Override
    public void configure(NotificationProviderConfig config) {
        try {
            messagingService = MessagingService.getInstance();
            String tokens[] = StringUtils.tokenize(config.getProperties().get(MailerConfigKey.RECIPIENTS.getKey()), ',');
            for (String t : tokens) {
                if (t.length() > 0)
                    recipients.add(t.trim());
            }
            htmlTemplateString = IOUtils.getInputStreamAsString(
                    ClassLoader.getSystemResourceAsStream(
                            config.getProperties().get(MailerConfigKey.THRESHOLD_ALERT_HTML_PATH.getKey())));
            plainTextTemplateString = IOUtils.getInputStreamAsString(
                    ClassLoader.getSystemResourceAsStream(
                            config.getProperties().get(MailerConfigKey.THRESHOLD_ALERT_TEXT_PATH.getKey())));
        } catch (Exception t) {
            log.warn("couldn't parse recipients from config  " + htmlTemplateString, t);
        }

    }

    @Override
    public void onNewAlert(ThresholdAlert alert) {

        HtmlMailMessage message = new HtmlMailMessage();
        message.setSender("moskito@anotheria.net");
        message.setSenderName("MoSKito Threshold Alert");
        message.setSubject("Threshold alert: " + alert);

        AlertThresholdTemplate alertThresholdTemplate = new AlertThresholdTemplate(alert);

        if (!StringUtils.isEmpty(plainTextTemplateString)) {
            message.setPlainTextContent(alertThresholdTemplate.process(plainTextTemplateString));
        } else {
            message.setPlainTextContent(ThresholdAlertMailUtil.alertToPlainText(alert));
        }
        if (!StringUtils.isEmpty(htmlTemplateString)) {
            message.setHtmlContent(alertThresholdTemplate.process(htmlTemplateString));
        }

        if (!StringUtils.isEmpty(plainTextTemplateString) || !StringUtils.isEmpty(htmlTemplateString)) {
            for (String r : recipients) {
                message.setRecipient(r);
                try {
                    boolean sent = messagingService.sendMessage(message);
                    if (!sent)
                        log.error("Can't send message to " + r);

                } catch (Exception e) {
                    log.error("onNewAlert(" + alert + "), to:  " + r + ")", e);
                }
            }
        } else {
            log.error("Couldn't send email - both plain text and html mail body not defined");
        }

    }
}
