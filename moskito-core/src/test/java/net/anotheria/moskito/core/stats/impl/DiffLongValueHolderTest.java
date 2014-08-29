package net.anotheria.moskito.core.stats.impl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.06.14 01:45
 */
public class DiffLongValueHolderTest {
	//this scenario tests a load where same amount is consumed each interval.
	@Test public void testContinuousLoad(){
		long start = 1000;
		long add = 1000;

		long increment = start;

		DiffLongValueHolder vh = new DiffLongValueHolder(IntervalRegistry.getInstance().getInterval("snapshot"));
		for (int i=0; i<10; i++){
			vh.setValueAsLong(increment);
			increment+=add;
			vh.intervalUpdated(IntervalRegistry.getInstance().getInterval("snapshot"));
			assertEquals(add, vh.getValueAsLong());
		}

	}
}
