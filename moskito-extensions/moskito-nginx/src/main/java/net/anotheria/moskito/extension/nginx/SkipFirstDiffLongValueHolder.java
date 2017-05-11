package net.anotheria.moskito.extension.nginx;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.DiffLongValueHolder;
import net.anotheria.moskito.core.stats.impl.DiffLongValueHolderFactory;

/**
 * Class was intended to hold diff values to produce requests-per-second metric,
 * However, the only usable additional functionality is skipping first diff(which
 * obviously become equal to totalValue otherwise).
 * Also, this class is only usable when value update period is equal to interval
 * or much smaller( allowing at least 3 value updates between interval updates).
 *
 * @author dzhmud
 * @see net.anotheria.moskito.core.stats.impl.RateValueHolder
 */
@SuppressWarnings("unused")
public final class SkipFirstDiffLongValueHolder extends DiffLongValueHolder {

    /** Factory that creates SkipFirstDiffLongValueHolder instances. */
    public static class SkipFirstDiffLongValueHolderFactory extends DiffLongValueHolderFactory {
        @Override protected DiffLongValueHolder createValueHolderObject(Interval aInterval) {
            return new SkipFirstDiffLongValueHolder(aInterval);
        }
    }

    /***/
    private volatile long lastCurrentValue = 0;
    /***/
    private volatile boolean firstIntervalSkipped = false;

    private SkipFirstDiffLongValueHolder(Interval anInterval){
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
