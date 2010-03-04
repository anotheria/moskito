package net.java.dev.moskito.central.demo;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Feb 26, 2010
 *         Time: 9:45:20 AM
 */
public class DemoServiceException extends Exception {

    public DemoServiceException() {
    }

    public DemoServiceException(String message) {
        super(message);
    }

    public DemoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoServiceException(Throwable cause) {
        super(cause);
    }
}
