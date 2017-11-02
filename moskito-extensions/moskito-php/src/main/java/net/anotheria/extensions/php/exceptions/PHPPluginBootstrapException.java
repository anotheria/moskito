package net.anotheria.extensions.php.exceptions;

/**
 * Thrown on MoskitoPHP plugin initialization fail
 */
public class PHPPluginBootstrapException extends RuntimeException {

    public PHPPluginBootstrapException(String message) {
        super(message);
    }

    public PHPPluginBootstrapException(String message, Throwable cause) {
        super(message, cause);
    }

}
