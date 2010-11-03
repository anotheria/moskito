package net.java.dev.moskito.core.blueprint;

import net.java.dev.moskito.core.predefined.RequestOrientedStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * The clue of this test is not to test the function of calc, but to ensure that it supplies the correct data to moskito itself.
 * @author another
 *
 */
public class BlueprintWithCalcTest {
	@Test public void executeCalcOperations() throws Exception{
		assertEquals(2, Calc.calculate("+", 1, 1));
		assertEquals(5, Calc.calculate("+", 2, 3));
		assertEquals(10, Calc.calculate("+", 6, 4));
		assertEquals(12, Calc.calculate("+", 6, 6));
		assertEquals(0, Calc.calculate("+", 0, 0));
		assertEquals(45, Calc.calculate("+", 40, 5));

		assertEquals(0, Calc.calculate("-", 1, 1));
		assertEquals(10, Calc.calculate("-", 11, 1));
		assertEquals(4, Calc.calculate("-", 5, 1));
		assertEquals(2, Calc.calculate("-", 3, 1));
		
		IStatsProducer plus = Calc.getProducer("+");
		IStats plusStats = plus.getStats().get(0);
		assertEquals(6, ((RequestOrientedStats)plusStats).getTotalRequests());

		IStatsProducer minus = Calc.getProducer("-");
		IStats minusStats = minus.getStats().get(0);
		assertEquals(4, ((RequestOrientedStats)minusStats).getTotalRequests());
	}
	
	@Test public void testErrorneousAction() throws Exception{
		try{
			Calc.calculate("E", 0, 0);
			fail("Exception expected");
		}catch(RuntimeException e){
			
		}

		IStatsProducer error = Calc.getProducer("E");
		IStats errorStats = error.getStats().get(0);
		assertEquals(1, ((RequestOrientedStats)errorStats).getTotalRequests());
		assertEquals(1, ((RequestOrientedStats)errorStats).getErrors());
	}
}
