package net.anotheria.moskito.core.blueprint;

import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
		
		IStatsProducer<RequestOrientedStats> plus = Calc.getProducer("+");
		RequestOrientedStats plusStats = plus.getStats().get(0);
		assertEquals(6, plusStats.getTotalRequests());

		IStatsProducer<RequestOrientedStats> minus = Calc.getProducer("-");
		RequestOrientedStats minusStats = minus.getStats().get(0);
		assertEquals(4, minusStats.getTotalRequests());
	}
	
	@Test public void testErroneousAction() throws Exception{
		try{
			Calc.calculate("E", 0, 0);
			fail("Exception expected");
		}catch(RuntimeException e){
			
		}

		IStatsProducer<RequestOrientedStats> error = Calc.getProducer("E");
		RequestOrientedStats errorStats = error.getStats().get(0);
		assertEquals(1, errorStats.getTotalRequests());
		assertEquals(1, errorStats.getErrors());
	}
}
