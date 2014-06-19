package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.communication.data.HtmlMailMessage;
import net.anotheria.communication.service.MessagingService;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.util.IOUtils;
import net.anotheria.moskito.extensions.notificationtemplate.AlertThresholdTemplate;
import net.anotheria.util.NumberUtils;
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

    private String templateString;

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(MailNotificationProvider.class);


	public MailNotificationProvider(){
		recipients = new ArrayList<String>();
	}

	@Override
	public void configure(NotificationProviderConfig config) {
		try{
			messagingService = MessagingService.getInstance();
			String tokens[] = StringUtils.tokenize(config.getProperties().get("recipients"), ',');
			for (String t : tokens){
				if (t.length()>0)
					recipients.add(t.trim());
			}
            templateString = IOUtils.getInputStreamAsString(ClassLoader.getSystemResourceAsStream(config.getProperties().get("templateUrl")));
		}catch(Exception t){
			log.warn("couldn't parse recipients from config  "+templateString, t);
		}

	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		String subject = "Threshold alert: "+alert;

		String mailtext = "Threshold alert:\n";
		mailtext += "Timestamp: "+alert.getTimestamp()+"\n";
		mailtext += "ISO Timestamp: "+ NumberUtils.makeISO8601TimestampString(alert.getTimestamp())+"\n";
		mailtext += "Threshold: "+alert.getThreshold()+"\n";
		mailtext += "OldStatus: "+alert.getOldStatus()+"\n";
		mailtext += "NewStatus: "+alert.getNewStatus()+"\n";
		mailtext += "OldValue: "+alert.getOldValue()+"\n";
		mailtext += "NewValue: "+alert.getNewValue()+"\n";


        HtmlMailMessage message = new HtmlMailMessage();

        message.setSender("moskito@anotheria.net");
        message.setSenderName("MoSKito Threshold Alert");
        message.setSubject(subject);
        message.setPlainTextContent(mailtext);
        AlertThresholdTemplate alertThresholdTemplate = new AlertThresholdTemplate(alert);
        message.setHtmlContent(alertThresholdTemplate.process(templateString));


		for (String r : recipients){
			message.setRecipient(r);
			try{
				boolean sent = messagingService.sendMessage(message);
				if (!sent)
					log.error("Can't send message to " + r);

			}catch(Exception e){
				log.error("onNewAlert("+alert+"), to:  "+r+")", e);
			}
		}

	}
}
