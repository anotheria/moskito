package net.anotheria.moskito.core.predefined;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.03.13 18:12
 */
public class ThreadCountStatsTest {
	@Test
	public void testGetValueByNameAsStringWithNull(){
		ThreadCountStats stats = new ThreadCountStats();
		stats.update(10, 5, 5);

		assertNull(stats.getValueByNameAsString(null,null, null));

	}

	@Test
	public void testMinMax(){
		ThreadCountStats stats = new ThreadCountStats();
		stats.update(10,5,5);
		stats.update(20,10,10);
		stats.update(30,15,1);
		stats.update(10,5,5);

		assertEquals(1, stats.getMinCurrent(null));
		assertEquals(10, stats.getMaxCurrent(null));

		String statString = stats.toStatsString();
		assertTrue(statString.indexOf("Current: 5")>-1);
	}
}
