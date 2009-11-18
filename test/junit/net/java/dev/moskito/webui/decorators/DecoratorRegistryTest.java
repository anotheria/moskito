package net.java.dev.moskito.webui.decorators;

import net.java.dev.moskito.core.predefined.ActionStats;
import net.java.dev.moskito.core.predefined.CacheStats;
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.predefined.MemoryPoolStats;
import net.java.dev.moskito.core.predefined.MemoryStats;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.util.storage.StorageStats;
import net.java.dev.moskito.web.session.SessionCountStats;
import net.java.dev.moskito.webui.decorators.predefined.ActionStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.CacheStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.FilterStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryPoolStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.ServiceStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.SessionCountDecorator;
import net.java.dev.moskito.webui.decorators.util.StorageStatsDecorator;

import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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
		testResolution(SessionCountStats.class, SessionCountDecorator.class);
		testResolution(StorageStats.class, StorageStatsDecorator.class);
	}
	
	private void testResolution(Class<? extends IStats> statsPattern, Class<? extends IDecorator> decoratorPattern) throws Exception{
		IDecorator resolvedDecorator = registry.getDecorator(statsPattern.newInstance());
		assertNotNull("Resolved decorator was null! ", resolvedDecorator);
		assertEquals("Resolved decorator is not of expected type", decoratorPattern, resolvedDecorator.getClass());
		//this is not necessarly a registry test, but it ensures that the returned type actually can basically handle pattern type.
		assertNotNull(resolvedDecorator.getValues(statsPattern.newInstance(), null, TimeUnit.MILLISECONDS));
	}
}
