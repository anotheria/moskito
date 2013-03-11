package net.anotheria.moskito.core.producers;

import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * A generic {@link IStats} type containing {@link TypeAwareStatValue}s.
 * 
 * @author Michael König
 */
public class GenericStats extends AbstractStats {

    /**
     * all the {@link TypeAwareStatValue}s.
     */
    private final NavigableMap<String, TypeAwareStatValue> values;

    /**
     * Constructs an instance of GenericStats.
     * 
     * @param aName
     *            name of the stats object.
     */
    public GenericStats(final String aName) {
        super(aName);
        this.values = new TreeMap<String, TypeAwareStatValue>();
    }

    /**
     * @return all the {@link TypeAwareStatValue}s
     */
    public Collection<TypeAwareStatValue> getAllValues() {
        return Collections.unmodifiableCollection(values.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAvailableValueNames() {
        // hmpf...
        final List<String> res = new ArrayList<String>(values.size());
        for (final String key : values.navigableKeySet()) {
            res.add(key);
        }
        return res;
    }

    /**
     * @param valueName
     * @return the {@link TypeAwareStatValue} instance related to given name
     */
    public TypeAwareStatValue getValueByName(final String valueName) {
        if (valueName == null) {
            throw new AssertionError("value name must not be null!");
        }

        return values.get(valueName.toLowerCase());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueByNameAsString(final String valueName,
                                         final String intervalName,
                                         final TimeUnit timeUnit) {
        final StatValue value = getValueByName(valueName);
        if (value == null) {
            return null;
        }

        return value.getValueAsString(intervalName);
    }

    /**
     * @return true if at least one value is set, false otherwise.
     */
    public boolean hasValues() {
        return !values.isEmpty();
    }

    /**
     * @param value
     *            the {@link TypeAwareStatValue}
     */
    public void putValue(final TypeAwareStatValue value) {
        final String name = value.getName();
        values.put(name.toLowerCase(), value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toStatsString(final String aIntervalName, final TimeUnit unit) {
        final StringBuilder b = new StringBuilder();
        b.append(getName()).append(' ');
        for (final Map.Entry<String, TypeAwareStatValue> entry : values.entrySet()) {
            final String key = entry.getKey();
            final StatValue value = entry.getValue();
            b.append(key).append(value.getValueAsString(aIntervalName));
        }
        return b.toString();
    }

}
