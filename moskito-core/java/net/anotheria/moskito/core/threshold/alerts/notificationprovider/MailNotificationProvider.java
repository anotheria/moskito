package net.anotheria.moskito.core.threshold.alerts.notificationprovider;

import net.anotheria.communication.data.SimpleMailMessage;
import net.anotheria.communication.service.MessagingService;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.12 15:48
 */
public class MailNotificationProvider implements NotificationProvider {

	private List<String> recipients;

	private static MessagingService messagingService;

	private static Logger log = Logger.getLogger(MailNotificationProvider.class);


	public MailNotificationProvider(){
		recipients = new ArrayList<String>();
	}

	@Override
	public void configure(String parameter) {
		try{
			messagingService = MessagingService.getInstance();
			String tokens[] = StringUtils.tokenize(parameter, ',');
			for (String t : tokens){
				if (t.length()>0)
					recipients.add(t.trim());
			}
		}catch(Throwable t){
			log.warn("couldn't parse recipients  "+parameter, t);
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

		SimpleMailMessage message = new SimpleMailMessage();


		message.setSender("moskito@anotheria.net");
		message.setSenderName("MoSKito Threshold Alert");
		message.setSubject(subject);
		message.setMessage(mailtext);

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
