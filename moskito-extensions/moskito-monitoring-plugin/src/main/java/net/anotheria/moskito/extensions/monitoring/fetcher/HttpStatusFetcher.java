package net.anotheria.moskito.extensions.monitoring.fetcher;

import net.anotheria.moskito.extensions.monitoring.config.MonitoredInstance;
import net.anotheria.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class that retrieves remote app status via HTTP(s). Supports authentication.
 *
 * @author dzhmud
 */
public class HttpStatusFetcher implements StatusFetcher<String> {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpStatusFetcher.class);

    /** Singleton instance. */
    private static final HttpStatusFetcher INSTANCE = new HttpStatusFetcher();

    /**
     * Fetch current status for the instance via HTTP(s).
     * @param instance {@link MonitoredInstance} to fetch status from.
     * @return retrieved status if server responded with response code 200,
     * or null otherwise(another response code or in case of {@link IOException}).
     */
    @Override
    public String fetchStatus(MonitoredInstance instance) {
        final UsernamePasswordCredentials credentials = StringUtils.isEmpty(instance.getUsername()) ? null :
                new UsernamePasswordCredentials(instance.getUsername(), instance.getPassword());
        try {
            final HttpResponse response = HttpHelper.getHttpResponse(instance.getLocation(), credentials);
            final String content = HttpHelper.getResponseContent(response);
            if (HttpHelper.isScOk(response)) {
                return content;
            } else {
                LOGGER.warn("Response status code is not 200! Apache location = "
                        + instance.getLocation() + ", response=" + content);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to fetch content of URL ["+ instance.getLocation()+"].", e);
        }
        return null;
    }

    /**
     * Singleton factory for {@link HttpStatusFetcher} class.
     */
    public static final StatusFetcherFactory<String> FACTORY = new StatusFetcherFactory<String>() {

        @Override
        public StatusFetcher<String> getStatusFetcher() {
            return INSTANCE;
        }

    };

}
