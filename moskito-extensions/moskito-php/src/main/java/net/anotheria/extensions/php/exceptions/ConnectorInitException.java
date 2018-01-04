package net.anotheria.extensions.php.exceptions;

/**
 * Thrown by
 * {@link net.anotheria.extensions.php.connectors.Connector}
 * to indicate connector initialization fail
 */
public class ConnectorInitException extends Exception {

    public ConnectorInitException() {
    }

    public ConnectorInitException(String message) {
        super(message);
    }

    public ConnectorInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectorInitException(Throwable cause) {
        super(cause);
    }

    public ConnectorInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
