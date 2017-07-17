package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test that checks DiffLongValueHolder feature: counting diff instead of absolute value.
 *
 * @author lrosenberg
 * @since 26.06.14 01:45
 */
public class DiffLongValueHolderTest {
	
	private final Interval snapshotInterval = IntervalRegistry.getInstance().getInterval("snapshotInterval");
	
	//this scenario tests a load where same amount is consumed each interval.
	@Test public void testContinuousLoad(){
		long start = 1000;
		long add = 1000;

		long increment = start;

		DiffLongValueHolder vh = new DiffLongValueHolder(snapshotInterval);
		for (int i=0; i<10; i++){
			vh.setValueAsLong(increment);
			increment+=add;
			vh.intervalUpdated(snapshotInterval);
			assertEquals(add, vh.getValueAsLong());
		}

	}

	//this scenario tests that valueholder is resetting correctly
	@Test
	public void testReseting() {
		final DiffLongValueHolder statValue = new DiffLongValueHolder(snapshotInterval);
		final ValueChecker value = new ValueChecker() {
			@Override
			public void shouldBe(long expected) {
				assertEquals(expected, statValue.getValueAsLong());
			}
		};

		//initial zero
		value.shouldBe(0);

		//regular value update
		statValue.setValueAsLong(100);
		value.shouldBe(0);

		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(100);
		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(0);

		//external value was reset - we get negative value as diff
		statValue.setValueAsLong(0);
		statValue.intervalUpdated(snapshotInterval);
		statValue.setValueAsLong(200);
		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(200);

		statValue.setValueAsLong(0);
		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(-200);

		//manually resetting holders, expecting them not to show negative values
		statValue.setValueAsLong(0);
		statValue.intervalUpdated(snapshotInterval);
		statValue.setValueAsLong(200);
		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(200);

		statValue.reset();

		statValue.setValueAsLong(0);
		statValue.intervalUpdated(snapshotInterval);
		value.shouldBe(0);//that's the goal wanted.


	}

	interface ValueChecker {
		void shouldBe(long expected);
	}

}
