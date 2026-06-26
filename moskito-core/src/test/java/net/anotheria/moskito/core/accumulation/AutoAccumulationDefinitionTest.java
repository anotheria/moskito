package net.anotheria.moskito.core.accumulation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class AutoAccumulationDefinitionTest {

	@Test
	public void testProducerNameTest() {
		AutoAccumulatorDefinition def = new AutoAccumulatorDefinition();
		def.setProducerNamePattern("(.*)ServiceImpl");
		Assertions.assertTrue(def.matches("ShopServiceImpl"));
		Assertions.assertFalse(def.matches("ShopControl"));
	}

	@Test
	public void testStatNameTest() {
		AutoAccumulatorDefinition def = new AutoAccumulatorDefinition();
		def.setStatNamePattern("(.*)");
		Assertions.assertTrue(def.statNameMatches("StatName"));
	}
}

