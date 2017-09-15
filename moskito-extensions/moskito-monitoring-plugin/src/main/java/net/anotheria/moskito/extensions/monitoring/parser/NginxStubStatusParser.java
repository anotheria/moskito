package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.extensions.monitoring.metrics.NginxMetrics;
import net.anotheria.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the Nginx stub status. Parses nginx response with regExp.
 *
 * @author dzhmud
 */
public final class NginxStubStatusParser implements StatusParser<String, StatusData> {

    /** Singleton instance. */
    public static final NginxStubStatusParser INSTANCE = new NginxStubStatusParser();

    private NginxStubStatusParser(){}

    /** RegExp matching nginx stub status. */
    private static final String STATUS_TEMPLATE =
            "^Active connections: ([\\d]+) \n" +
            "server accepts handled requests\n" +
            " ([\\d]+) ([\\d]+) ([\\d]+) \n" +
            "Reading: ([\\d]+) Writing: ([\\d]+) Waiting: ([\\d]+)\\s*$";

    /** Compiled pattern. */
    private static final Pattern pattern = Pattern.compile(STATUS_TEMPLATE);

    /**
     * Parse NGINX stub status into StatusData&lt;NginxMetrics&gt;.
     * @param nginxStatus retrieved stub status.
     * @return parsed metrics, put into StatusData.
     */
    public StatusData<NginxMetrics> parse(String nginxStatus) {
        StatusData<NginxMetrics> status = new StatusData<>();
        if (StringUtils.isEmpty(nginxStatus)) {
            throw new IllegalArgumentException("nginx status is empty!");
        } else {
            Matcher matcher = pattern.matcher(nginxStatus);
            if (matcher.matches()) {
                status.put(NginxMetrics.ACTIVE, Long.parseLong(matcher.group(1)));
                Long accepted = Long.parseLong(matcher.group(2));
                status.put(NginxMetrics.ACCEPTED, accepted);
                status.put(NginxMetrics.ACCEPTEDPERSECOND, accepted);
                Long handled = Long.parseLong(matcher.group(3));
                status.put(NginxMetrics.HANDLED, handled);
                status.put(NginxMetrics.HANDLEDPERSECOND, handled);
                Long dropped = handled - accepted;
                status.put(NginxMetrics.DROPPED, dropped);
                status.put(NginxMetrics.DROPPEDPERSECOND, dropped);
                Long requests = Long.parseLong(matcher.group(4));
                status.put(NginxMetrics.REQUESTS, requests);
                status.put(NginxMetrics.REQUESTSPERSECOND, requests);
                status.put(NginxMetrics.READING, Long.parseLong(matcher.group(5)));
                status.put(NginxMetrics.WRITING, Long.parseLong(matcher.group(6)));
                status.put(NginxMetrics.WAITING, Long.parseLong(matcher.group(7)));
            } else {
                String message = "NginxStubStatusParser can't parse nginx status response: ["+nginxStatus + "]";
                throw new IllegalArgumentException(message);
            }
        }
        return status;
    }

}
