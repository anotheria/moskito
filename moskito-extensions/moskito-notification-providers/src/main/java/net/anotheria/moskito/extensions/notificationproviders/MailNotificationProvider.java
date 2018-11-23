package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.communication.data.HtmlMailMessage;
import net.anotheria.communication.service.MessagingService;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.util.IOUtils;
import net.anotheria.moskito.extensions.notificationtemplate.ThresholdAlertTemplate;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
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
        recipients = new ArrayList<>();
    }

    @Override
    public void configure(NotificationProviderConfig config) {
        try {
            messagingService = MessagingService.getInstance();
            String tokens[] = StringUtils.tokenize(config.getProperties().get(NotificationProviderConfigKey.RECIPIENTS.getKey()), ',');
            for (String t : tokens) {
                if (t.length() > 0)
                    recipients.add(t.trim());
            }

            try {
				String htmlTemplateStringName = config.getProperties().get(NotificationProviderConfigKey.HTML_TEMPLATE_PATH.getKey());
				if (htmlTemplateStringName != null && htmlTemplateStringName.length() > 0) {
					htmlTemplateString = getFileContent(htmlTemplateStringName);
				}
			}catch(IOException e){
            	log.warn("Can't get html template '"+config.getProperties().get(NotificationProviderConfigKey.HTML_TEMPLATE_PATH.getKey())+"' config ", e);
			}
			try{
				String plainTextTemplateStringName = config.getProperties().get(NotificationProviderConfigKey.TEXT_TEMPLATE_PATH.getKey());
				if (plainTextTemplateStringName!=null && plainTextTemplateStringName.length()>0) {
					plainTextTemplateString = getFileContent(plainTextTemplateStringName);
				}
			}catch(IOException e){
				log.warn("Can't get text template '"+config.getProperties().get(NotificationProviderConfigKey.TEXT_TEMPLATE_PATH.getKey())+"' config ", e);
			}

        } catch (Exception t) {
            log.warn("couldn't parse recipients or templates from config  " + htmlTemplateString, config);
        }

    }

    private String getFileContent(String name) throws IOException{
		final ClassLoader myLoader = getClass().getClassLoader();
		final URL url = myLoader.getResource(name);
		return IOUtils.getInputStreamAsString(
				myLoader.getResourceAsStream(name)
		);


	}

    @Override
    public void onNewAlert(ThresholdAlert alert) {

        HtmlMailMessage message = new HtmlMailMessage();
        message.setSender("moskito@anotheria.net");
        message.setSenderName("MoSKito Threshold Alert");
        message.setSubject("Threshold alert: " + alert);

        ThresholdAlertTemplate thresholdAlertTemplate = new ThresholdAlertTemplate(alert);

        if (!StringUtils.isEmpty(plainTextTemplateString)) {
            message.setPlainTextContent(thresholdAlertTemplate.process(plainTextTemplateString));
        } else {
            message.setPlainTextContent(ThresholdAlertConverter.toPlainText(alert));
        }
        if (!StringUtils.isEmpty(htmlTemplateString)) {
            message.setHtmlContent(thresholdAlertTemplate.process(htmlTemplateString));
        }

		for (String r : recipients) {
			message.setRecipient(r);
			try {
				boolean sent = messagingService.sendMessage(message);
				if (!sent)
					log.error("Can't send message to " + r);

			} catch (Exception e) {
				log.error("onNewAlert(" + alert + "), to:  " + r + ')', e);
			}
		}

    }
}
