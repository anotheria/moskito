package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
	
	@Test public void testFailingListenerDoesNotAffectTheOthers(){
		IntervalImpl i = new IntervalImpl(1, "blub", 1000*60);
		MyIntervalListener firstPrimary = new MyIntervalListener();
		MyIntervalListener lastPrimary = new MyIntervalListener();
		MyIntervalListener secondary = new MyIntervalListener();

		i.addPrimaryIntervalListener(firstPrimary);
		i.addPrimaryIntervalListener(aCaller -> { throw new RuntimeException("i am a broken listener"); });
		i.addPrimaryIntervalListener(lastPrimary);
		i.addSecondaryIntervalListener(secondary);

		i.update();

		assertEquals(1, firstPrimary.updatecount);
		assertEquals(1, lastPrimary.updatecount, "a failing listener must not prevent the following listeners from being notified");
		assertEquals(1, secondary.updatecount, "a failing primary listener must not prevent the secondary listeners from being notified");
	}

	@Test public void testListenerFailingWithErrorDoesNotEscapeUpdate(){
		//an Error escaping update() would kill the Timer thread driving the updates, stopping all intervals for good.
		IntervalImpl i = new IntervalImpl(1, "blub", 1000*60);
		MyIntervalListener secondary = new MyIntervalListener();

		i.addPrimaryIntervalListener(aCaller -> { throw new StackOverflowError("i am a very deep listener"); });
		i.addSecondaryIntervalListener(secondary);

		i.update();

		assertEquals(1, secondary.updatecount);
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
