package net.anotheria.extensions.php.exceptions;

/**
 * Thrown when {@link net.anotheria.extensions.php.mappers.Mapper} instance
 * can`t update producer using incoming data
 */
public class MappingException extends Exception {

    public MappingException() {
    }

    public MappingException(String message) {
        super(message);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

    public MappingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
