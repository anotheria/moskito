/**
 * (c) 2012 König-Software GmbH - http://www.koenig-software.de
 */
package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * This class implements a ValueHolder that is able to process double values.
 * 
 * @author Michael König
 */
public class DoubleValueHolder extends AbstractValueHolder {

    /**
     * This is the value that will be used as defaultValue if no other one was specified.
     */
    public static final double DEFAULT_DEFAULT_VALUE = 0d;

    /**
     * This attribute stores the current value supporting concurrent access.
     */
    private double currentValue;

    /**
     * The value measured in the last interval.
     */
    private double lastValue;

    /**
     * This is the default value that this ValueHolder contains after a reset.
     */
    private double defaultValue;

    /**
     * Constructs an instance of DoubleValueHolder.
     * 
     * @param aInterval
     *            {@link Interval}
     */
    public DoubleValueHolder(Interval aInterval) {
        super(aInterval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void intervalUpdated(Interval aCaller) {
        lastValue = currentValue;
        currentValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        currentValue = defaultValue;
        lastValue = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increase() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrease() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getValueAsInt() {
        return (int) Math.rint(lastValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getValueAsLong() {
        return Math.round(lastValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValueAsDouble() {
        return lastValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValueAsString() {
        return String.valueOf(lastValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentValueAsInt() {
        return (int) Math.rint(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCurrentValueAsLong() {
        return Math.round(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCurrentValueAsDouble() {
        return currentValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrentValueAsString() {
        return String.valueOf(currentValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAsInt(int aValue) {
        currentValue = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAsString(String aValue) {
        currentValue = Double.parseDouble(aValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAsLong(long aValue) {
        currentValue = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValueAsDouble(double aValue) {
        currentValue = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseByInt(int aValue) {
        currentValue += aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseByLong(long aValue) {
        currentValue += aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseByDouble(double aValue) {
        currentValue += aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseByInt(int aValue) {
        currentValue -= aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseByLong(long aValue) {
        currentValue -= aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseByDouble(double aValue) {
        currentValue -= aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsInt(int aValue) {
        defaultValue = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsLong(long aValue) {
        defaultValue = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultValueAsDouble(double aValue) {
        defaultValue = aValue;
    }

}
