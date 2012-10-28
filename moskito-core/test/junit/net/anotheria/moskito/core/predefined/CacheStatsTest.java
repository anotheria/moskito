package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.10.12 20:45
 */
public class CacheStatsTest {
	@Test public void testHitRate(){
		CacheStats stats = new CacheStats();
		for (int i=0; i<100; i++){
			stats.addRequest();
			//each second request a hit
			if (i%2==0)
				stats.addHit();
		}
		assertEquals(100, stats.getRequests(null));
		assertEquals(50, stats.getHits(null));
		assertEquals(0.5, stats.getHitRatio(null), 0.0001);
	}

	@Test public void getValueByNameAsStringNullTest(){
		CacheStats s = new CacheStats();
		try{
			s.getValueByNameAsString(null, null, (TimeUnit)null);
			fail("Expected assertion error");
		}catch(AssertionError e){

		}
	}

	@Test public void getValueNamesTest(){
		List<String> names = new CacheStats().getAvailableValueNames();
		//we don't have to check ALL.
		assertTrue(names.contains("REQ"));
		assertTrue(names.contains("WR"));
		assertTrue(names.contains("EX"));
	}



	@Test public void getValueByNameAsStringTest(){
		CacheStats s = new CacheStats();

		//prepare data

		for (int i=0; i<10; i++)
			s.addRequest();

		for (int i=0; i<5; i++)
			s.addHit();

		for (int i=0; i<7; i++)
			s.addWrite();

		for (int i=0; i<20; i++)
			s.addGarbageCollected();
		for (int i=0; i<25; i++)
			s.addExpired();
		for (int i=0; i<30; i++)
			s.addDelete();
		for (int i=0; i<35; i++)
			s.addRollover();
		for (int i=0; i<40; i++)
			s.addFiltered();
		for (int i=0; i<45; i++)
			s.addCacheFull();
		//now check
		assertEquals("10", s.getValueByNameAsString("REQ", null, TimeUnit.SECONDS));
		assertEquals("5", s.getValueByNameAsString("HIT", null, TimeUnit.SECONDS));
		assertEquals("0.5", s.getValueByNameAsString("HR", null, TimeUnit.SECONDS));
		assertEquals("7", s.getValueByNameAsString("WR", null, TimeUnit.SECONDS));

		assertEquals("20", s.getValueByNameAsString("GC", null, TimeUnit.SECONDS));
		assertEquals("25", s.getValueByNameAsString("EX", null, TimeUnit.SECONDS));
		assertEquals("30", s.getValueByNameAsString("DEL", null, TimeUnit.SECONDS));
		assertEquals("35", s.getValueByNameAsString("RO", null, TimeUnit.SECONDS));
		assertEquals("40", s.getValueByNameAsString("FI", null, TimeUnit.SECONDS));
		assertEquals("45", s.getValueByNameAsString("FU", null, TimeUnit.SECONDS));

	}

	@Test public void testToStatsString(){
		CacheStats s = new CacheStats();

		//prepare data

		for (int i=0; i<10; i++)
			s.addRequest();

		for (int i=0; i<5; i++)
			s.addHit();

		for (int i=0; i<7; i++)
			s.addWrite();

		for (int i=0; i<20; i++)
			s.addGarbageCollected();
		for (int i=0; i<25; i++)
			s.addExpired();
		for (int i=0; i<30; i++)
			s.addDelete();
		for (int i=0; i<35; i++)
			s.addRollover();
		for (int i=0; i<40; i++)
			s.addFiltered();
		for (int i=0; i<45; i++)
			s.addCacheFull();

		String stats = s.toStatsString();
		assertTrue(stats.indexOf("REQ: 10")!=-1);
		assertTrue(stats.indexOf("HIT: 5")!=-1);
		assertTrue(stats.indexOf("HR: 0.5")!=-1);
		assertTrue(stats.indexOf("WR: 7")!=-1);
		assertTrue(stats.indexOf("GC: 20")!=-1);
		assertTrue(stats.indexOf("EX: 25")!=-1);
		assertTrue(stats.indexOf("DEL: 30")!=-1);
		assertTrue(stats.indexOf("RO: 35")!=-1);
		assertTrue(stats.indexOf("FI: 40")!=-1);
		assertTrue(stats.indexOf("FU: 45")!=-1);

	}
}
