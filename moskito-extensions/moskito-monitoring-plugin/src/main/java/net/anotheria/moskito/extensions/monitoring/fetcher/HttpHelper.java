package net.anotheria.moskito.extensions.monitoring.fetcher;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;

/**
 * This help class is a wrapper around apache http client lib.
 *
 * @author lrosenberg
 * @since 15.07.13 11:14
 */
public final class HttpHelper {

    private HttpHelper(){}

    /**
     * HttpClient instance.
     */
    private static AbstractHttpClient httpClient = null;

    static{
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(
                new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(
                new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);

        httpClient = new DefaultHttpClient(cm);
    }

    /**
     * Executes a request using the given URL. If response has status code of 200(SC_OK)
     * returns content of its HttpEntity, otherwise - {@code null}.
     * @param url the http URL to connect to.
     *
     * @return content of HttpEntity from response.
     * @throws IOException in case of a problem or the connection was aborted
     * @throws ClientProtocolException in case of an http protocol error
     * @see #isScOk(HttpResponse)
     */
    public static String getURLContent(String url) throws IOException {
        return getResponseContent(getHttpResponse(url));

    }

    /**
     * Executes a request using the given URL.
     *
     * @param url  the http URL to connect to.
     *
     * @return  the response to the request.
     * @throws IOException in case of a problem or the connection was aborted
     * @throws ClientProtocolException in case of an http protocol error
     */
    public static HttpResponse getHttpResponse(String url) throws IOException {
        return getHttpResponse(url, null);
    }

    /**
     * Executes a request using the given URL and credentials.
     *
     * @param url  the http URL to connect to.
     * @param credentials credentials to use
     *
     * @return  the response to the request.
     * @throws IOException in case of a problem or the connection was aborted
     * @throws ClientProtocolException in case of an http protocol error
     */
    public static HttpResponse getHttpResponse(String url, UsernamePasswordCredentials credentials) throws IOException {
        HttpGet request = new HttpGet(url);
        if (credentials != null) {
            URI uri = request.getURI();
            AuthScope authScope = new AuthScope(uri.getHost(), uri.getPort());
            Credentials cached = httpClient.getCredentialsProvider().getCredentials(authScope);
            if (!areSame(cached, credentials)) {
                httpClient.getCredentialsProvider().setCredentials(authScope, credentials);
            }
        }
        return httpClient.execute(request);
    }

    /**
     * Compare two instances of Credentials.
     * @param c1 instance of Credentials
     * @param c2 another instance of Credentials
     * @return comparison result. {@code true} if both are null or contain same user/password pairs, false otherwise.
     */
    private static boolean areSame(Credentials c1, Credentials c2) {
        if (c1 == null) {
            return c2 == null;
        } else {
            return StringUtils.equals(c1.getUserPrincipal().getName(), c1.getUserPrincipal().getName()) &&
                    StringUtils.equals(c1.getPassword(), c1.getPassword());
        }
    }

    /**
     * Check if HttpResponse contains status code 200(SC_OK).
     * @param response instance of HttpResponse.
     * @return {@code true} if HttpResponse contains status code 200(SC_OK), {@code false} otherwise.
     */
    public static boolean isScOk(HttpResponse response) {
        return response != null && response.getStatusLine()!= null &&
                response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
    }

    /**
     * Get text content from the response if response status code is 200.
     * @param response instance of HttpResponse.
     * @return String representation of HttpEntity.
     * @throws IOException if an I/O error occurs
     * @see #isScOk(HttpResponse)
     */
    public static String getResponseContent(HttpResponse response) throws IOException {
        final HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                entity.writeTo(out);//call this in any case!!!
                return isScOk(response) ? new String(out.toByteArray(), Charset.forName("UTF-8")) : null;
            } finally {
                //ensure entity is closed.
                EntityUtils.consume(entity);
            }
        } else {
            return null;
        }
    }

}
