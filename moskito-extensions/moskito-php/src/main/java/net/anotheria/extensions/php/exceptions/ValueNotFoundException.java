package net.anotheria.extensions.php.exceptions;

/**
 * Thrown by {@link net.anotheria.extensions.php.mappers.StatsValues}
 * methods like {@link net.anotheria.extensions.php.mappers.StatsValues#getAsDouble(String)}
 * if value with given key is not present
 */
public class ValueNotFoundException extends Exception {

    public ValueNotFoundException(String message) {
        super(message);
    }

}
