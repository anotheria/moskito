package net.java.dev.moskito.core.treshold;

import org.junit.Test;
import static org.junit.Assert.*;

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
