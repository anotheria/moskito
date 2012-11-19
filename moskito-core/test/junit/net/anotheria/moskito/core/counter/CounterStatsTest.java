package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 18.11.12 00:09
 */
public class CounterStatsTest {
	@Test public void createBusinessStatisticsCounterStat(){
		CounterStats stats = new CounterStats("registrations");
		stats.inc();
		stats.inc();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		stats.inc();
		stats.inc();
		stats.inc();

		assertEquals(5, stats.get());
		assertEquals(2, stats.get("snapshot"));

	}

	@Test public void createBusinessStatisticsProducer() throws Exception{
		OnDemandStatsProducer<CounterStats> producer = new OnDemandStatsProducer<CounterStats>("business", "counter", "business", new CounterStatsFactory());
		//now test
		producer.getStats("registration").inc();
		producer.getStats("login").inc();

		producer.getStats("registration").inc();
		producer.getStats("login").inc();

		producer.getStats("registration").inc();
		producer.getStats("login").inc();
		producer.getStats("payment").inc();

		assertEquals(3, producer.getStats("registration").get());
		assertEquals(3, producer.getStats("login").get());
		assertEquals(1, producer.getStats("payment").get());

		//note cumulated will be zero because we don't add it, but we should as next example shows
		assertEquals(0, producer.getStats("cumulated").get());
	}

	@Test public void createPaymentStatisticsProducer() throws Exception{
		OnDemandStatsProducer<CounterStats> producer = new OnDemandStatsProducer<CounterStats>("payment", "counter", "payment", new CounterStatsFactory());
		//now test cc payment
		producer.getDefaultStats().inc();//payment occured
		producer.getStats("cc").inc();

		producer.getDefaultStats().inc();//payment occured
		producer.getStats("cc").inc();

		//ec payment
		producer.getDefaultStats().inc();//payment occured
		producer.getStats("ec").inc();

		assertEquals(2, producer.getStats("cc").get());
		assertEquals(1, producer.getStats("ec").get());


		//But we had a total of 3 payments. The following two lines are identical.
		assertEquals(3, producer.getStats("cumulated").get());
		assertEquals(3, producer.getDefaultStats().get());
	}

	@Test public void testGetValueByName() throws Exception{
		OnDemandStatsProducer<CounterStats> producer = new OnDemandStatsProducer<CounterStats>("payment", "counter", "payment", new CounterStatsFactory());
		//now test cc payment
		producer.getStats("cc").inc();

		assertEquals("1", producer.getStats("cc").getValueByNameAsString("counter", null, TimeUnit.SECONDS));
	}

}
