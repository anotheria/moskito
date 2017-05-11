package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Designed to measure values on 'per-second' basis.
 * Use only with values that are constantly increasing or decreasing but not both.
 *
 * @author dzhmud
 */
public class RateValueHolder extends AbstractValueHolder {

    /**
     * Immutable value holder for single long value with automatic timestamp.
     */
    private static final class ValueSnapshot {
        /** ValueSnapshot creation time.*/
        private final long timestamp = System.currentTimeMillis();
        /** Value itself.*/
        private final long value;

        private ValueSnapshot(long value) {
            this.value = value;
        }

        private boolean isOlderThen(ValueSnapshot other) {
            return timestamp < other.timestamp;
        }
    }

    /** Previously processed value. */
    private volatile ValueSnapshot previousValue;
    /** Current value. */
    private volatile ValueSnapshot currentValue;
    /** Last calculated value rate. */
    private volatile double lastRateValue;

    /** Synchronization object.*/
    private final Object lock = new Object();
    /** Decimal format for converting resulting double value into String representation. */
    private final DecimalFormat df = new DecimalFormat("#.#");

    /**
     * Constructs an instance of RateValueHolder.
     *
     * @param aInterval {@link Interval}
     */
    RateValueHolder(Interval aInterval) {
        super(aInterval);
        df.setRoundingMode(RoundingMode.CEILING);
    }

    @Override
    public void intervalUpdated(Interval aCaller) {
        synchronized (lock) {
            if (previousValue != null && currentValue != null && previousValue.isOlderThen(currentValue)) {
                double diff = currentValue.value - previousValue.value;
                long duration = (currentValue.timestamp - previousValue.timestamp) / 1000;
                if (duration > 0) {
                    lastRateValue = diff / duration;
                    previousValue = currentValue;
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        synchronized (lock) {
            previousValue = null;
            currentValue = null;
            lastRateValue = 0;
        }
    }

    /**
     * This method shall increment the value by one.
     */
    @Override
    public void increase() {
        throw new UnsupportedOperationException("RateValueHolder does not allow direct change of calculated rate value.");
    }

    /**
     * This method shall decrement the value by one.
     */
    @Override
    public void decrease() {
        throw new UnsupportedOperationException("RateValueHolder does not allow direct change of calculated rate value.");
    }

    /**
     * This method shall return the current value as int.
     *
     * @return the current value
     */
    @Override
    public int getValueAsInt() {
        return (int)getValueAsDouble();
    }

    /**
     * This method shall return the current value as long.
     *
     * @return the current value
     */
    @Override
    public long getValueAsLong() {
        return (long)getValueAsDouble();
    }

    /**
     * This method shall return the current value as double.
     *
     * @return the current value
     */
    @Override
    public double getValueAsDouble() {
        return lastRateValue;
    }

    @Override
    public String getValueAsString() {
        synchronized (df) {
            return df.format(lastRateValue);
        }
    }

    /**
     * This method shall return the current value as int for internal use only.
     *
     * @return the current value
     */
    @Override
    public int getCurrentValueAsInt() {
        return (int)getCurrentValueAsLong();
    }

    /**
     * This method shall return the current value as long for internal use only.
     *
     * @return the current value
     */
    @Override
    public long getCurrentValueAsLong() {
        return currentValue != null ? currentValue.value : 0;
    }

    /**
     * This method shall return the current value as double for internal use only.
     *
     * @return the current value
     */
    @Override
    public double getCurrentValueAsDouble() {
        return getCurrentValueAsLong();
    }

    @Override
    public String getCurrentValueAsString() {
        return Long.toString(getCurrentValueAsLong());
    }

    /**
     * This method shall assign the given value to the current value.
     *
     * @param aValue the new value
     */
    @Override
    public void setValueAsInt(int aValue) {
       setValueAsLong(aValue);
    }

    /**
     * This method shall assign the given value to the current value.
     *
     * @param aValue the new value
     */
    @Override
    public void setValueAsString(String aValue) {
        setValueAsLong(Long.parseLong(aValue));
    }

    /**
     * This method shall assign the given value to the current value.
     *
     * @param aValue the new value
     */
    @Override
    public void setValueAsLong(long aValue) {
        synchronized (lock) {
            currentValue = new ValueSnapshot(aValue);
            if (previousValue == null)
                previousValue = currentValue;
        }
    }

    /**
     * This method shall assign the given value to the current value.
     *
     * @param aValue the new value
     */
    @Override
    public void setValueAsDouble(double aValue) {
        setValueAsLong((long)aValue);
    }

    /**
     * This method shall increment the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void increaseByInt(int aValue) {
        increaseByLong(aValue);
    }

    /**
     * This method shall increment the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void increaseByLong(long aValue) {
        setValueAsLong(getCurrentValueAsLong() + aValue);
    }

    /**
     * This method shall increment the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void increaseByDouble(double aValue) {
        increaseByLong((long)aValue);
    }

    /**
     * This method shall decrement the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void decreaseByInt(int aValue) {
        decreaseByLong(aValue);
    }

    /**
     * This method shall decrement the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void decreaseByLong(long aValue) {
        setValueAsLong(getCurrentValueAsLong() - aValue);
    }

    /**
     * This method shall decrement the current value by the given amount.
     *
     * @param aValue the amount
     */
    @Override
    public void decreaseByDouble(double aValue) {
        decreaseByLong((long)aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsInt(int aValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsLong(long aValue) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsDouble(double aValue) {
        throw new UnsupportedOperationException();
    }

}
