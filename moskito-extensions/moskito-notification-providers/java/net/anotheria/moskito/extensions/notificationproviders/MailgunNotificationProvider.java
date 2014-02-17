package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * MailGun notification provider.
 *
 * @author lrosenberg
 * @since 17.02.14 17:04
 */
public class MailgunNotificationProvider implements NotificationProvider {

	/**
	 * The system property for the api key for mailgun api.
	 */
	public static final String SYSTEM_PROPERTY_API_KEY = MailgunNotificationProvider.class.getSimpleName()+"APIKey";

	/**
	 * System property for sender's address.
	 */
	public static final String SYSTEM_PROPERTY_SENDER = MailgunNotificationProvider.class.getSimpleName()+"Sender";

	/**
	 * Preinstalled api key, if no other api key is configured.
	 */
	private static final String DEFAULT_API_KEY = "key-72d3spjfr06x0knhupx4z1v0e2bgjfk3";

	/**
	 * Default sender.
	 */
	private static final String DEFAULT_SENDER  = "MoSKito Alerts <moskito-alert@moskito.org>";

	/**
	 * List of the recipients for the alerts.
	 */
	private ArrayList<String> recipients = null;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(MailgunNotificationProvider.class);

	/**
	 * Used api key at runtime.
	 */
	private final String apiKey;

	/**
	 * Used sender at runtime.
	 */
	private final String sender;

	/**
	 * Client instance. This one is shared between messages.
	 */
	private Client client;

	/**
	 * Creates a new MailgunNotificationProvider.
	 */
	public MailgunNotificationProvider(){
		client = Client.create();

		String anApiKey = System.getProperty(SYSTEM_PROPERTY_API_KEY);
		if (anApiKey==null){
			log.info("Using default api key, set -D"+SYSTEM_PROPERTY_API_KEY+"=<api_key> if you want to use own key");
			anApiKey = DEFAULT_API_KEY;
		}

		apiKey = anApiKey;
		sender = System.getProperty(SYSTEM_PROPERTY_SENDER, DEFAULT_SENDER);
		log.debug("Sender is "+sender);
		client.addFilter(new HTTPBasicAuthFilter("api", apiKey));

	}

	@Override
	public void configure(String parameter) {
		recipients = new ArrayList<String>();
		String tokens[] = StringUtils.tokenize(parameter, ',');
		for (String t : tokens){
			if (t.length()>0)
				recipients.add(t.trim());
		}
	}

	@Override
	public void onNewAlert(ThresholdAlert alert) {
		String subject = "Threshold alert: "+alert;

		String mailText = "Threshold alert:\n";
		mailText += "Timestamp: "+alert.getTimestamp()+"\n";
		mailText += "ISO Timestamp: "+ NumberUtils.makeISO8601TimestampString(alert.getTimestamp())+"\n";
		mailText += "Threshold: "+alert.getThreshold()+"\n";
		mailText += "OldStatus: "+alert.getOldStatus()+"\n";
		mailText += "NewStatus: "+alert.getNewStatus()+"\n";
		mailText += "OldValue: "+alert.getOldValue()+"\n";
		mailText += "NewValue: "+alert.getNewValue()+"\n";

		WebResource webResource = client.resource("https://api.mailgun.net/v2/moskito.org/messages");

		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "MoSKito Alerts <moskito-alert@moskito.org>");
		for (String r : recipients){
			formData.add("to", r);
		}
		formData.add("subject", subject);
		formData.add("text", mailText);

		try{
			ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);

			if (response.getStatus()!=200){
		 		log.warn("Couldn't send email, status expected 200, got "+response.getStatus());
			}else{
				byte data[] = new byte[response.getLength()];
				response.getEntityInputStream().read(data);
				log.debug("Successfully sent notification mail "+new String(data));

			}
		}catch(Exception e){
			log.error("Couldn't send email", e);
		}
	}
}
