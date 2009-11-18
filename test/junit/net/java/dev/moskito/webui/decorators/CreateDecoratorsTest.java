package net.java.dev.moskito.webui.decorators;

import net.java.dev.moskito.webui.decorators.predefined.ActionStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.CacheStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.FilterStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryPoolStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.ServiceStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.SessionCountDecorator;
import net.java.dev.moskito.webui.decorators.util.StorageStatsDecorator;

import org.junit.Test;

/**
 * This test simply tests whether decorators can be created ensuring that the amount of elements in the explanation, short_explanation and captions arrays are the same.
 * @author another
 *
 */
public class CreateDecoratorsTest {
	@Test public void createPredefinedDecorators(){
		new ActionStatsDecorator();
		new CacheStatsDecorator();
		new FilterStatsDecorator();
		new MemoryPoolStatsDecorator();
		new MemoryStatsDecorator();
		new ServiceStatsDecorator();
		new SessionCountDecorator();
	}
	
	@Test public void createUtilDecorators(){
		new StorageStatsDecorator();
	}
}
