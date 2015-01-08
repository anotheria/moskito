package net.anotheria.moskito.extensions.producers;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.10.14 22:17
 */
public class RollingOnDemandStatsProducerTest {
	@Test public void testRollingOnDemandStatsProducerTest() throws Exception{
		//OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<ServiceStats>("id", "cat", "sub", ServiceStatsFactory.DEFAULT_INSTANCE);
		RollingOnDemandStatsProducer<ServiceStats> producer = new RollingOnDemandStatsProducer<ServiceStats>("id", "cat", "sub", ServiceStatsFactory.DEFAULT_INSTANCE, 10);
		//11 iterations, we should only have 10 values afterwards, the first should be evicted.
		for (int a = 0; a<11; a++){
			producer.getStats("name"+a).notifyRequestFinished();
		}


		//10 producer values + cumulated.
		assertEquals(10 + 1, producer.getStats().size());
		boolean found5 = false, found0 = false;
		for (ServiceStats ss : producer.getStats()) {
			if (ss.getName().equals("name0"))
				found0 = true;
			if (ss.getName().equals("name5"))
				found5 = true;
		}

		assertTrue("We should have found the stats number 5", found5);
		assertFalse("We should have found the stats number 5", found0);

		long sizeBeforeAdd = IntervalRegistry.getInstance().getInterval("1m").getPrimaryListenerCount();
		producer.getStats("StatsThatWeDidn'tHadYet");
		long sizeAfterAdd  = IntervalRegistry.getInstance().getInterval("1m").getPrimaryListenerCount();
		assertEquals(sizeBeforeAdd, sizeAfterAdd);
	}
}
