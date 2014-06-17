package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import net.anotheria.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    public void configure(final String parameter) {
        try {
            JSONObject config = new JSONObject(parameter);

            user = config.getString("user");
            password = config.getString("password");

            StringBuilder sb = new StringBuilder();

            JSONArray array = config.getJSONArray("recipients");

            for (int i = 0; i < array.length(); i++) {
                if (i != 0) {
                    sb.append(",");
                }

                sb.append(array.getString(i));
            }

            recipients = sb.toString();
        } catch (JSONException e) {
            throw new RuntimeException("Can't parse");
        }
    }

    @Override
    public void onNewAlert(ThresholdAlert alert) {
        final String query = formatQuery(alert);

        try {
            URLConnection conn = PROVIDER_URL.openConnection();
            conn.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream())) {
                writer.write(query);
                writer.flush();
            }

            String response = IOUtils.readInputStreamBufferedAsString(conn.getInputStream(), ENCODING);

            LOGGER.info("onNewAlert(): Request sent: [" + query + "]. Response received: [" + response + "].");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Formats query.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @return query
     */
    private String formatQuery(final ThresholdAlert alert) {
        return String.format(SMS_QUERY_FORMAT, user, password, createMessage(alert), recipients); // TODO: improve not to replace user/password/etc every time
    }

    /**
     * Create SMS message.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @return SMS
     */
    private static String createMessage(final ThresholdAlert alert) {
        // TODO: message should be encoded to apropriate encoding
        return alert.getThreshold().getName() + ": " + alert.getOldStatus() + "->" + alert.getNewStatus();
    }
}
