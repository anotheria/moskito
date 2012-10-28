package net.anotheria.moskito.core.registry.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.10.12 20:28
 */
public class FiltersTest {

	private List<IStatsProducer> dummies;

	@Before public void setup(){
		dummies = new ArrayList<IStatsProducer>();
		dummies.add(new DummyProducer("1", "category1", "subsystem1"));
		dummies.add(new DummyProducer("2", "category1", "subsystem2"));
		dummies.add(new DummyProducer("3", "category2", "subsystem3"));
		dummies.add(new DummyProducer("4", "category2", "subsystem1"));
		dummies.add(new DummyProducer("5", "category3", "subsystem2"));
		dummies.add(new DummyProducer("6", "category3", "subsystem3"));
	}

	@Test public void testCategoryFilter(){
		List<IStatsProducer> producers = filter(new CategoryFilter("category1"));
		assertEquals(2, producers.size());
		assertEquals("category1", producers.get(0).getCategory());
		assertEquals("category1", producers.get(1).getCategory());
	}

	@Test public void testSubsystemFilter(){
		List<IStatsProducer> producers = filter(new SubsystemFilter("subsystem2"));
		assertEquals(2, producers.size());
		assertEquals("subsystem2", producers.get(0).getSubsystem());
		assertEquals("subsystem2", producers.get(1).getSubsystem());
	}

	@Test public void testAllThroughFilter(){
		List<IStatsProducer> producers = filter(new AllThroughFilter());
		assertEquals(dummies.size(), producers.size());
	}


	private List<IStatsProducer> filter(IProducerFilter filter){
		ArrayList<IStatsProducer> ret = new ArrayList<IStatsProducer>();
		for (IStatsProducer p : dummies){
			if (filter.doesFit(p))
				ret.add(p);
		}
		return ret;
	}
}
