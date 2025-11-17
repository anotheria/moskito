package net.anotheria.moskito.core.journey;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class JourneyManagerFactoryTest {
	@Test public void testFactory(){
		JourneyManager manager = JourneyManagerFactory.getJourneyManager();
		assertTrue(manager instanceof JourneyManager);
	}
}
