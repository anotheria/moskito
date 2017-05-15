package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.Interval;
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

}
