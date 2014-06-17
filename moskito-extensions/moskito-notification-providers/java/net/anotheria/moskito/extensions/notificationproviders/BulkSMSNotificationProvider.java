package net.anotheria.moskito.extensions.notificationproviders;

import net.anotheria.moskito.core.threshold.alerts.NotificationProvider;
import net.anotheria.moskito.core.threshold.alerts.ThresholdAlert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            throw new RuntimeException("Invalid Format");
        }
    }

    @Override
    public void onNewAlert(ThresholdAlert alert) {
        final String query = formatQuery(alert);

        OutputStreamWriter writer = null;
        BufferedReader reader = null;

        try {
            URLConnection conn = new URL("http://bulksms.vsms.net:5567/eapi/submission/send_sms/2/2.0").openConnection(); // TODO: extract URL to static final field
            conn.setDoOutput(true);

            writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(query);
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            LOGGER.info("onNewAlert(): Request sent: [" + query + "]. Response received: [" + sb.toString() +"]."); // TODO: change to debug
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // ignored
            }
        }
    }

    /**
     * Formats query.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @return query
     */
    private String formatQuery(final ThresholdAlert alert) {
        return String.format(SMS_QUERY_FORMAT, user, password, createMessage(alert), recipients);
    }

    /**
     * Create SMS message.
     *
     * @param alert {@link net.anotheria.moskito.core.threshold.alerts.ThresholdAlert}
     * @return SMS
     */
    private static String createMessage(final ThresholdAlert alert) {
        return alert.getThreshold().getName() + ": " + alert.getOldStatus() + "->" + alert.getNewStatus();
    }
}
