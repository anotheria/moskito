package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.decorators.predefined.ActionStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.CacheStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.FilterStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.MemoryPoolStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.MemoryStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.ServiceStatsDecorator;
import net.anotheria.moskito.core.decorators.util.StorageStatsDecorator;
import net.anotheria.moskito.core.predefined.ActionStats;
import net.anotheria.moskito.core.predefined.CacheStats;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.predefined.MemoryPoolStats;
import net.anotheria.moskito.core.predefined.MemoryStats;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.util.storage.StorageStats;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class DecoratorRegistryTest {
	
	private static IDecoratorRegistry registry;
	@BeforeClass public static void init(){
		registry = DecoratorRegistryFactory.getDecoratorRegistry();
	}
	
	@Test public void testResolution() throws Exception{
		testResolution(ActionStats.class, ActionStatsDecorator.class);
		testResolution(CacheStats.class, CacheStatsDecorator.class);
		testResolution(FilterStats.class, FilterStatsDecorator.class);
		testResolution(MemoryPoolStats.class, MemoryPoolStatsDecorator.class);
		testResolution(MemoryStats.class, MemoryStatsDecorator.class);
		testResolution(ServiceStats.class, ServiceStatsDecorator.class);
		testResolution(StorageStats.class, StorageStatsDecorator.class);
	}
	
	private void testResolution(Class<? extends IStats> statsPattern, Class<? extends IDecorator> decoratorPattern) throws Exception{
		IDecorator resolvedDecorator = registry.getDecorator(statsPattern);
		assertNotNull("Resolved decorator was null! ", resolvedDecorator);
		assertEquals("Resolved decorator is not of expected type", decoratorPattern, resolvedDecorator.getClass());
		//this is not necessarily a registry test, but it ensures that the returned type actually can basically handle pattern type.
		assertNotNull(resolvedDecorator.getValues(statsPattern.newInstance(), null, TimeUnit.MILLISECONDS));
	}
}
