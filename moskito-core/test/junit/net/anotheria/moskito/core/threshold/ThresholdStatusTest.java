package net.anotheria.moskito.core.threshold;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThresholdStatusTest {
	@Test public void testoverrule(){
		assertTrue(ThresholdStatus.PURPLE.overrules(ThresholdStatus.RED));
		assertTrue(ThresholdStatus.PURPLE.overrules(ThresholdStatus.GREEN));
		assertTrue(ThresholdStatus.RED.overrules(ThresholdStatus.YELLOW));
		assertTrue(ThresholdStatus.ORANGE.overrules(ThresholdStatus.YELLOW));
		assertTrue(ThresholdStatus.RED.overrules(ThresholdStatus.ORANGE));
		assertFalse(ThresholdStatus.GREEN.overrules(ThresholdStatus.ORANGE));
	}
}
