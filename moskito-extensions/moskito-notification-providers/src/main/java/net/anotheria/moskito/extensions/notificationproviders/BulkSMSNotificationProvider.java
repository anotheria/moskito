package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.moskito.extensions.notificationtemplate.ThresholdAlertTemplate;
import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 'Bulk SMS' Notification Provider. For more info visit http://www.bulksms.com/.
 *
 * @author Alex Osadchy
 * @author Oleg Potapenko
 * @author Roman Potopa
 */
public class BulkSMSNotificationProvider implements NotificationProvider {

    /**
     * {@link org.slf4j.Logger}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BulkSMSNotificationProvider.class);

    /**
     * Provider encoding.
     */
    private static final String ENCODING = "ISO-8859-1";

    /**
     * {@link java.net.URL} to provider.
     */
    private static final URL PROVIDER_URL;

    /**
     * SMS text template.
     */
    private String smsTemplate;

    /**
     * Static init.
     */
    static {
        try {
            PROVIDER_URL = new URL("http://bulksms.vsms.net:5567/eapi/submission/send_sms/2/2.0");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Query format.
     */
    public static final String SMS_QUERY_FORMAT = "username=%s&password=%s&message=%s&msisdn=%s&want_report=1";

    /**
     * User name.
     */
    private String user;

    /**
     * Password.
     */
    private String password;

    /**
     * CSV string with recipients.
     */
    private String recipients;

    @Override
    public void configure(NotificationProviderConfig config) {
		user = config.getProperties().get("user");
		password = config.getProperties().get("password");
		recipients = config.getProperties().get("recipients");
        try {
            smsTemplate = net.anotheria.moskito.core.util.IOUtils.getInputStreamAsString(
                ClassLoader.getSystemResourceAsStream(
                        config.getProperties().get("templatePath")));
        } catch (Throwable e) {
            LOGGER.warn("configure(): couldn't read template file", e);
        }
    }

    @Override
    public void onNewAlert(ThresholdAlert alert) {
        final String query = formatQuery(alert);

        OutputStreamWriter writer = null;

        try {
            URLConnection conn = PROVIDER_URL.openConnection();
            conn.setDoOutput(true);

            writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(query);
            writer.flush();

            String response = IOUtils.readInputStreamBufferedAsString(conn.getInputStream(), ENCODING);

            LOGGER.info("onNewAlert(): Request sent: [" + query + "]. Response received: [" + response + "].");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeIgnoringException(writer);
        }
    }

    /**
     * Formats query.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @return query
     */
    private String formatQuery(final ThresholdAlert alert) {
        return String.format(SMS_QUERY_FORMAT, user, password, createMessage(alert, smsTemplate), recipients); // TODO: improve not to replace user/password/etc every time
    }

    /**
     * Create SMS message.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @param template {@link net.anotheria.moskito.extensions.notificationproviders.BulkSMSNotificationProvider#smsTemplate}
     * @return SMS
     */
    private static String createMessage(final ThresholdAlert alert, final String template) {
        // TODO: message should be encoded to apropriate encoding
        //return alert.getThreshold().getName() + ": " + alert.getOldStatus() + "->" + alert.getNewStatus();
        String message;
        if(!StringUtils.isEmpty(template)) {
            ThresholdAlertTemplate thresholdAlertTemplate = new ThresholdAlertTemplate(alert);
            message = thresholdAlertTemplate.process(template);
        } else {
            message = ThresholdAlertConverter.toPlainText(alert);
        }
        return message;
    }
}
