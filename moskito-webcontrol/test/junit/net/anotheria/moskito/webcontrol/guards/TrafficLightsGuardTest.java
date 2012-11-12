package net.anotheria.moskito.webcontrol.guards;

import static junit.framework.Assert.*;
import net.anotheria.moskito.webcontrol.guards.Condition;
import net.anotheria.moskito.webcontrol.guards.TrafficLightsGuard;
import net.anotheria.moskito.webcontrol.repository.LongAttribute;

import org.junit.Test;

public class TrafficLightsGuardTest {
	
	@Test
	public void testParseRules() throws Exception {
		TrafficLightsGuard guard = new TrafficLightsGuard();
		guard.setRules("..50,50..100,100..");
		
		LongAttribute attr = new LongAttribute("dima", 100);
		
		guard.execute(null, null, attr);
		
		assertNotNull(guard.getRules());
		assertEquals(3, guard.getRules().size());
		assertEquals(Condition.YELLOW, attr.getCondition());
		
	}
	
}
