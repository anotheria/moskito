package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author dzhmud
 */
public class SkipFirstDiffLongValueHolderTest {

    @Test
    public void test() {
        final Interval interval = DefaultIntervals.ONE_MINUTE;

        AbstractValueHolder value = SkipFirstDiffLongValueHolderFactory.INSTANCE.createValueHolderObject(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getCurrentValueAsLong());

        final long FIRST_PORTION = 10000L;

        value.setValueAsLong(FIRST_PORTION);

        assertEquals(0, value.getValueAsLong());
        assertEquals(FIRST_PORTION, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(FIRST_PORTION, value.getValueAsLong());
        assertEquals(FIRST_PORTION, value.getCurrentValueAsLong());

        long CURRENT_COUNT = FIRST_PORTION + 1000;

        value.setValueAsLong(CURRENT_COUNT);

        long EXPECTED = CURRENT_COUNT - FIRST_PORTION;

        assertEquals(FIRST_PORTION, value.getValueAsLong());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(EXPECTED, value.getValueAsLong());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

    }

    @Test
    public void testSnapshotInterval() {
        final Interval interval = IntervalRegistry.getInstance().getInterval(Constants.PREFIX_SNAPSHOT_INTERVAL);

        SkipFirstDiffLongValueHolder value = SkipFirstDiffLongValueHolderFactory.INSTANCE.createValueHolderObject(interval);

        long initial = 32455646345L;
        value.setValueAsLong(initial);
        value.intervalUpdated(interval);
        assertEquals(initial, value.getCurrentValueAsLong());
        assertEquals(0, value.getValueAsInt());

        long increment = 500;
        value.increaseByLong(increment);
        value.intervalUpdated(interval);
        assertEquals(initial + increment, value.getCurrentValueAsLong());
        assertEquals(increment, value.getValueAsInt());

        value.intervalUpdated(interval);
        assertEquals(initial + increment, value.getCurrentValueAsLong());
        assertEquals(0, value.getValueAsInt());

    }

    @Test
    @Ignore("DiffLong and childs does not work correctly with 'default' interval.")
    public void testDefaultInterval() {
        final StatValue statValue = createStatValue();
        final Interval interval = IntervalRegistry.getInstance().getInterval("default", 0);

        long initial = 32455646345L;
        statValue.setValueAsLong(initial);
        assertEquals(0, statValue.getValueAsLong());
        assertEquals(0, statValue.getValueAsLong(interval.getName()));

        long increment = 500;
        statValue.increaseByLong(increment);
        assertEquals(increment, statValue.getValueAsLong());
        assertEquals(increment, statValue.getValueAsLong(interval.getName()));

        statValue.increaseByLong(increment);
        assertEquals(2*increment, statValue.getValueAsLong());
        assertEquals(2*increment, statValue.getValueAsLong(interval.getName()));

    }

    private static StatValue createStatValue() {
        final IValueHolderFactory factory = SkipFirstDiffLongValueHolderFactory.INSTANCE;
        final TypeAwareStatValue statValue = new TypeAwareStatValueImpl("test", StatValueTypes.DIFFLONG, factory);
        for (Interval interval : Constants.getDefaultIntervals()) {
            statValue.addInterval(interval);
        }
        return statValue;
    }

}
