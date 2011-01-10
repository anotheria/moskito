package net.java.dev.moskito.core.accumulation;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccumulatorTest {
	@Test public void testAddValue(){
		AccumulatorDefinition def = new AccumulatorDefinition();
		Accumulator acc = new Accumulator(def);
		
		int limit = 200;
		def.setAccumulationAmount(limit);
		
		for (int i=0; i<limit; i++){
			acc.addValue(""+i);
			assertEquals(i+1, acc.getValues().size());
		}
		
		assertEquals(limit, acc.getValues().size());
		assertEquals("0", acc.getValues().get(0).getValue());
		
		for (int i=0; i<20; i++){
			acc.addValue("x"+i);
			assertEquals(limit+i+1, acc.getValues().size());
		}
		
		//adding next one should rotate back to 200
		acc.addValue("abc");
		assertEquals(limit, acc.getValues().size());
		
		//System.out.println(acc.getValues().get(0));
		//System.out.println(acc.getValues().get(200-1));
		//System.out.println(acc.getValues().get(200-2));
	}
} 
