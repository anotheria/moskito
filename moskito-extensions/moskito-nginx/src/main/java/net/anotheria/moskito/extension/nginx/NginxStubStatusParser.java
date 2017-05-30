package net.anotheria.moskito.extension.nginx;

import net.anotheria.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the Nginx stub status. Parses nginx response with regExp.
 *
 * @author dzhmud
 */
final class NginxStubStatusParser {

    private NginxStubStatusParser(){}

    /** RegExp matching nginx stub status. */
    private static final String STATUS_TEMPLATE =
            "^Active connections: ([\\d]+) \n" +
            "server accepts handled requests\n" +
            " ([\\d]+) ([\\d]+) ([\\d]+) \n" +
            "Reading: ([\\d]+) Writing: ([\\d]+) Waiting: ([\\d]+)\\s*$";

    /** Compiled pattern. */
    private static final Pattern pattern = Pattern.compile(STATUS_TEMPLATE);

    static NginxStatus parseStatus(String nginxStatus) throws NginxStatusParserException {
        NginxStatus status;
        if (StringUtils.isEmpty(nginxStatus)) {
            throw new NginxStatusParserException("nginx status is empty!");
        } else {
            Matcher matcher = pattern.matcher(nginxStatus);
            if (matcher.matches()) {
                status = NginxStatus.newBuilder()
                        .setActive(Long.parseLong(matcher.group(1)))
                        .setAccepted(Long.parseLong(matcher.group(2)))
                        .setHandled(Long.parseLong(matcher.group(3)))
                        .setRequests(Long.parseLong(matcher.group(4)))
                        .setReading(Long.parseLong(matcher.group(5)))
                        .setWriting(Long.parseLong(matcher.group(6)))
                        .setWaiting(Long.parseLong(matcher.group(7)))
                        .build();
            } else {
                String message = "NginxStubStatusParser can't parse nginx status response: ["+nginxStatus + "]";
                throw new NginxStatusParserException(message);
            }
        }
        return status;
    }

    /**
     * Checked Exception thrown when NginxStubStatusParser is unable to parse given text.
     */
    static final class NginxStatusParserException extends Exception {
        private NginxStatusParserException(String message) {
            super(message);
        }
    }
}
