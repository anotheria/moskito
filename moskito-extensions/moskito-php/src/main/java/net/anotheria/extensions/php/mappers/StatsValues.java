package net.anotheria.extensions.php.mappers;

import java.util.HashMap;
import java.util.Map;

import net.anotheria.extensions.php.exceptions.ValueNotFoundException;

/**
 * Stores stats values data.
 */
public class StatsValues extends HashMap<String, Object> {

    /**
     * Helper method to parse string representation
     * of value item to number or boolean type
     * @param value string representation of value item
     * @return parsed value item
     */
    private Object parseValue(String value) {

        // Try to parse value as number
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException ignored){}

        if (value.equals("true") || value.equals("false"))
            return Boolean.valueOf(value);

        throw new IllegalArgumentException("Only number or boolean stats values allowed");

    }

    /**
     * Constructor to instantiate values map by map
     * with strings values items representation
     *
     * @param m source of values
     */
    public StatsValues(Map<? extends String, String> m) {

		for (java.util.Map.Entry<? extends String, String> entry : m.entrySet())
            put(entry.getKey(), parseValue(entry.getValue()));

    }

    /**
     * Returns value by given key as long
     * @param key key of value
     * @return long value by given key
     * @throws ClassCastException if value type is not numeric
     * @throws ValueNotFoundException if value with such key is not found
     */
    public long getAsLong(String key) throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            throw new ValueNotFoundException("Value with key " + key + " is not found");

        return ((Number) value).longValue();

    }

    /**
     * Returns value by given key as double
     * @param key key of value
     * @return double value by given key
     * @throws ClassCastException if value type is not numeric
     * @throws ValueNotFoundException if value with such key is not found
     */
    public double getAsDouble(String key) throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            throw new ValueNotFoundException("Value with key " + key + " is not found");

        return ((Number) value).doubleValue();

    }

    /**
     * Returns value by given key as boolean
     * @param key key of value
     * @return boolean value by given key
     * @throws ClassCastException if value type is not boolean
     * @throws ValueNotFoundException if value with such key is not found
     */
    public boolean getAsBoolean(String key) throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            throw new ValueNotFoundException("Value with key " + key + " is not found");

        return ((Boolean) value);

    }

    /**
     * Returns value by given key as long
     * or second method argument if value with such key is not present
     * @param key key of value
     * @param defaultValue to be returned if value with given key is not present
     * @return long value by given key or default value
     * @throws ClassCastException if value present and it type is not long
     */
    public long getAsLongOrDefault(String key, long defaultValue)
            throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            return defaultValue;

        return ((Number) value).longValue();

    }

    /**
     * Returns value by given key as double
     * or second method argument if value with such key is not present
     * @param key key of value
     * @param defaultValue to be returned if value with given key is not present
     * @return long value by given key or default value
     * @throws ClassCastException if value present and it type is not long
     */
    public double getAsDoubleOrDefault(String key, double defaultValue)
            throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            return defaultValue;

        return ((Number) value).doubleValue();

    }

    /**
     * Returns value by given key as boolean
     * or second method argument if value with such key is not present
     * @param key key of value
     * @param defaultValue to be returned if value with given key is not present
     * @return long value by given key or default value
     * @throws ClassCastException if value present and it type is not boolean
     */
    public boolean getAsBooleanOrDefault(String key, boolean defaultValue)
            throws ClassCastException, ValueNotFoundException {

        Object value = get(key);

        if(value == null)
            return defaultValue;

        return ((Boolean) value);

    }

}
