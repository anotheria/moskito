package net.anotheria.moskito.extension.nginx;

import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.Interval;
import static org.junit.Assert.*;

import net.anotheria.moskito.core.stats.impl.DiffLongValueHolder;
import org.junit.Test;

/**
 * @author dzhmud
 */
public class SkipFirstDiffLongValueHolderTest {

    @Test
    public void test() {
        final Interval interval = DefaultIntervals.ONE_MINUTE;

        DiffLongValueHolder value = new SkipFirstDiffLongValueHolder.SkipFirstDiffLongValueHolderFactory()
                .createValueHolderObject(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getLastValue());
        assertEquals(0, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getLastValue());
        assertEquals(0, value.getCurrentValueAsLong());

        final long FIRST_PORTION = 10000L;

        value.setValueAsLong(FIRST_PORTION);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getLastValue());
        assertEquals(FIRST_PORTION, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(FIRST_PORTION, value.getValueAsLong());
        assertEquals(FIRST_PORTION, value.getLastValue());
        assertEquals(FIRST_PORTION, value.getCurrentValueAsLong());

        long CURRENT_COUNT = FIRST_PORTION + 1000;

        value.setValueAsLong(CURRENT_COUNT);

        long EXPECTED = CURRENT_COUNT - FIRST_PORTION;

        assertEquals(FIRST_PORTION, value.getValueAsLong());
        assertEquals(FIRST_PORTION, value.getLastValue());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(EXPECTED, value.getValueAsLong());
        assertEquals(EXPECTED, value.getLastValue());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

        value.intervalUpdated(interval);

        assertEquals(0, value.getValueAsLong());
        assertEquals(0, value.getLastValue());
        assertEquals(CURRENT_COUNT, value.getCurrentValueAsLong());

    }

}
