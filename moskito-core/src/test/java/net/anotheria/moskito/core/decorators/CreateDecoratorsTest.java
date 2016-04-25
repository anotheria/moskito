package net.anotheria.moskito.core.decorators;

import net.anotheria.moskito.core.decorators.predefined.ActionStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.CacheStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.FilterStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.MemoryPoolStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.MemoryStatsDecorator;
import net.anotheria.moskito.core.decorators.predefined.ServiceStatsDecorator;
import net.anotheria.moskito.core.decorators.util.SessionCountDecorator;
import net.anotheria.moskito.core.decorators.util.StorageStatsDecorator;
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
