package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

/**
 * Extended DiffLongValueHolder that throws away first interval diff.
 * Useful in cases when monitored value already have big absolute value at the moment of
 * producer creation.
 *
 * @author dzhmud
 */
public final class SkipFirstDiffLongValueHolder extends DiffLongValueHolder {

    /***/
    private volatile long lastCurrentValue = 0;
    /***/
    private volatile boolean firstIntervalSkipped = false;

    SkipFirstDiffLongValueHolder(Interval anInterval) {
        super(anInterval);
    }

    @Override
    public void intervalUpdated(Interval caller) {
        long currentCurrentValue = getCurrentValueAsLong();
        if (!firstIntervalSkipped) {
            firstIntervalSkipped = true;
            lastCurrentValue = currentCurrentValue;
        } else {
            long diff = currentCurrentValue - lastCurrentValue;
            lastCurrentValue = currentCurrentValue;
            setLastValue(diff);
        }
    }


}
