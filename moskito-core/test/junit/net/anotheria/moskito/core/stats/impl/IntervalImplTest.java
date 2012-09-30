package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntervalImplTest {
	@Test public void testListener(){
		IntervalImpl i = new IntervalImpl(1, "blub", 1000*60);
		MyIntervalListener primary = new MyIntervalListener();
		MyIntervalListener secondary = new MyIntervalListener();
		
		i.update();
		assertEquals(0, primary.updatecount);
		assertEquals(0, secondary.updatecount);
		
		i.addPrimaryIntervalListener(primary);
		i.update();
		assertEquals(1, primary.updatecount);
		assertEquals(0, secondary.updatecount);
	
		i.addSecondaryIntervalListener(secondary);
		i.update();
		assertEquals(2, primary.updatecount);
		assertEquals(1, secondary.updatecount);
		
		i.removePrimaryIntervalListener(primary);
		i.update();
		assertEquals(2, primary.updatecount);
		assertEquals(2, secondary.updatecount);
		
		i.removeSecondaryIntervalListener(secondary);
		i.update();
		assertEquals(2, primary.updatecount);
		assertEquals(2, secondary.updatecount);
		
	}
	
	@Test public void testBasics(){
		IntervalImpl i = new IntervalImpl(1, "blub", 1000*60);
		assertEquals(1, i.getId());
		assertEquals("blub", i.getName());
		assertEquals(60000, i.getLength());
		assertNotNull(i.toString());
	}
	
	private static class MyIntervalListener implements IIntervalListener{

		int updatecount = 0;
		
		@Override
		public void intervalUpdated(Interval aCaller) {
			updatecount++;
		}
	}
}
