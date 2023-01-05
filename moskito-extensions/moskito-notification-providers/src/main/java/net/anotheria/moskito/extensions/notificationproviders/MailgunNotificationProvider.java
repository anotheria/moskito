package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.core.util.IOUtils;
import net.anotheria.moskito.extensions.notificationtemplate.ThresholdAlertTemplate;
import net.anotheria.util.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
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
    public static final String SYSTEM_PROPERTY_API_KEY = MailgunNotificationProvider.class.getSimpleName() + "APIKey";

    /**
     * System property for sender's address.
     */
    public static final String SYSTEM_PROPERTY_SENDER = MailgunNotificationProvider.class.getSimpleName() + "Sender";

    /**
     * Preinstalled api key, if no other api key is configured.
     */
    private static final String DEFAULT_API_KEY = "key-72d3spjfr06x0knhupx4z1v0e2bgjfk3";

    /**
     * Default sender.
     */
    private static final String DEFAULT_SENDER = "MoSKito Alerts <moskito-alert@moskito.org>";

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
     * Html mail template.
     */
    private String htmlTemplate;

    /**
     * Plain text mail template.
     */
    private String plainTextTemplate;

    /**
     * Client instance. This one is shared between messages.
     */
    private Client client;

    /**
     * Creates a new MailgunNotificationProvider.
     */
    public MailgunNotificationProvider() {
        String anApiKey = System.getProperty(SYSTEM_PROPERTY_API_KEY);
        if (anApiKey == null) {
            log.info("Using default api key, set -D" + SYSTEM_PROPERTY_API_KEY + "=<api_key> if you want to use own key");
            anApiKey = DEFAULT_API_KEY;
        }

        apiKey = anApiKey;
        sender = System.getProperty(SYSTEM_PROPERTY_SENDER, DEFAULT_SENDER);
        log.debug("Sender is " + sender);

        ClientConfig config = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().nonPreemptive().credentials("api", anApiKey).build();
        config.register(feature);
        client = ClientBuilder.newClient(config);


    }

    @Override
    public void configure(NotificationProviderConfig config) {
        try {
            String tokens[] = StringUtils.tokenize(config.getProperties().get(NotificationProviderConfigKey.RECIPIENTS.getKey()), ',');
            recipients = new ArrayList<>(tokens.length);
            for (String t : tokens) {
                if (t.length() > 0)
                    recipients.add(t.trim());
            }
            htmlTemplate = IOUtils.getInputStreamAsString(
                    ClassLoader.getSystemResourceAsStream(
                            config.getProperties().get(NotificationProviderConfigKey.HTML_TEMPLATE_PATH.getKey())));
            plainTextTemplate = IOUtils.getInputStreamAsString(
                    ClassLoader.getSystemResourceAsStream(
                            config.getProperties().get(NotificationProviderConfigKey.TEXT_TEMPLATE_PATH.getKey())));
        } catch (Throwable e) {
            log.warn("couldn't initialize config  " + config, e);
        }
    }

    @Override
    public void onNewAlert(ThresholdAlert alert) {
        String subject = "Threshold alert: " + alert;

        try {
            WebTarget webResource = client.target("https://api.mailgun.net/v2/moskito.org/messages");

            MultivaluedMapImpl formData = new MultivaluedMapImpl();
            formData.add("from", "MoSKito Alerts <moskito-alert@moskito.org>");
            for (String r : recipients) {
                formData.add("to", r);
            }
            formData.add("subject", subject);

            ThresholdAlertTemplate thresholdAlertTemplate = new ThresholdAlertTemplate(alert);
            if(!StringUtils.isEmpty(plainTextTemplate)) {
                formData.add("text", thresholdAlertTemplate.process(plainTextTemplate));
            } else {
                formData.add("text", ThresholdAlertConverter.toPlainText(alert));
            }
            if(!StringUtils.isEmpty(htmlTemplate)) {
                formData.add("html", thresholdAlertTemplate.process(htmlTemplate));
            }

                try {
                    ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);

                    if (response.getStatus() != 200) {
                        log.warn("Couldn't send email, status expected 200, got " + response.getStatus());
                    } else {
                        byte data[] = new byte[response.getLength()];
                        log.debug("Successfully sent notification mail " + new String(data));

                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    log.error("Couldn't send email", e);
                }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Couldn't send email", e);
        }
    }

}
