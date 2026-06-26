package net.anotheria.moskito.core.journey;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class JourneyManagerFactoryTest {
	@Test public void testFactory(){
		JourneyManager manager = JourneyManagerFactory.getJourneyManager();
		assertTrue(manager instanceof JourneyManager);
	}
}
