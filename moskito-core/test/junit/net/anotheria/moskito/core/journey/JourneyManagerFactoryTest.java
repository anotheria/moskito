package net.anotheria.moskito.core.journey;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 27.10.12 23:39
 */
public class JourneyManagerFactoryTest {
	@Test public void testFactory(){
		JourneyManager manager = JourneyManagerFactory.getJourneyManager();
		assertTrue(manager instanceof JourneyManager);
	}
}
