package net.anotheria.moskito.core.accumulation;

import org.junit.Assert;
import org.junit.Test;


public class AutoAccumulationDefinitionTest {

	@Test
	public void testProducerNameTest() {
		AutoAccumulatorDefinition def = new AutoAccumulatorDefinition();
		def.setProducerNamePattern("(.*)ServiceImpl");
		Assert.assertTrue(def.matches("ShopServiceImpl"));
		Assert.assertFalse(def.matches("ShopControl"));
	}

	@Test
	public void testStatNameTest() {
		AutoAccumulatorDefinition def = new AutoAccumulatorDefinition();
		def.setStatNamePattern("(.*)");
		Assert.assertTrue(def.statNameMatches("StatName"));
	}
}

