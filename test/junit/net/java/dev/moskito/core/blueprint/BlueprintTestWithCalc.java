package net.java.dev.moskito.core.blueprint;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * The clue of this test is not to test the function of calc, but to ensure that it supplies the correct data to moskito itself.
 * @author another
 *
 */
public class BlueprintTestWithCalc {
	@Test public void executeCalcOperations() throws Exception{
		assertEquals(2, Calc.calculate("+", 1, 1));
	}
}
