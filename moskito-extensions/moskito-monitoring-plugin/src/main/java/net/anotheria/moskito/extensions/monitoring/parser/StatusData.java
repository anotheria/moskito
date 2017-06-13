package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.extensions.monitoring.metrics.IGenericMetrics;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class for Map that contains parsed metrics.
 * Null keys are not allowed, and values are checked.
 * Class checks that values are of correct type for the corresponding metrics
 * and automatically converts them if possible, before storing.
 * If value is neither of correct type, neither can be parsed automatically -
 * method will throw {@link IllegalArgumentException}.
 *
 * @param <T> type of keys.
 *
 * @author dzhmud
 */
public class StatusData<T extends IGenericMetrics> {

    /** Inner structure represented by regular HashMap. */
    private final HashMap<T, Object> data = new HashMap<>();

    /**
     * Stores given key-value mapping if no restrictions are violated by them.
     * @param key mapping key.
     * @param value mapping value.
     * @throws IllegalArgumentException if the given key is {@code null} or if value is neither of correct type nor of String type.
     */
    public void put(T key, Object value) {
        data.put(key, ensureValueCorrectness(key, value));
    }

    /**
     * Stores every mapping from the given map.
     * Implemented in 'fail-fast' manner: no rollbacks of already stored mappings, fails at first occurred violation.
     * @param m map containing data to put in this StatusData.
     * @throws IllegalArgumentException if any mapping in the given map violates this class restrictions.
     */
    public void putAll(Map<? extends T, ?> m) {
        for (Map.Entry<? extends T, ?> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Get value stored under given key.
     * @param key key to find value for.
     * @return value stored under that key or null if there is no mapping for that key or if there is null stored
     * for that key.
     * @throws IllegalArgumentException if key passed is null.
     */
    public Object get(T key) {
        checkKey(key);
        return data.get(key);
    }

    /**
     * Ensure that value is of correct type for the given key.
     * If value is not already of correct type but of type String, attempt parsing into correct type.
     * @return provided value if it's correctly typed or parsed value of correct type or {@code null} if auto-conversion failed.
     * @throws IllegalArgumentException if the given key is {@code null} or if value is neither of correct type nor of String type.
     */
    private Object ensureValueCorrectness(T key, Object value) {
        checkKey(key);
        if (key.isCorrectValue(value) || value == null) {
            return value;
        }
        if (value instanceof String) {
            value = key.parseValue(String.class.cast(value));
            return value;
        }
        throw new IllegalArgumentException("Entry <'"+key+"','"+value+"'> rejected! Value should be of correct type or of type String(for auto-parsing)!");
    }

    /** Null keys are not allowed.*/
    private void checkKey(T key) {
        if (key == null) throw new IllegalArgumentException("Null keys are not allowed!");
    }

}
