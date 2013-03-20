package net.anotheria.moskito.core.snapshot;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.03.13 14:38
 */
public class SnapshotRepositoryTest extends BaseSnapshotTest{

	@Before
	public void setup(){
		System.setProperty("JUNITTEST", "true");
	}

	@Test
	public void testConsumeCycle() throws Exception{
		MySnapshotConsumer consumer = new MySnapshotConsumer();
		assertEquals(0, consumer.getConsumedCount());

		SnapshotRepository.getInstance().addConsumer(consumer);

		String intervalName = "5m";
		//force interval update
		forceIntervalUpdate(intervalName);

		OnDemandStatsProducer<ServiceStats> producer = setupProducer();
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

		//add first stat.
		ServiceStats stat1 =  producer.getStats("case1");
		stat1.addRequest();
		stat1.addExecutionTime(100); //100 milliseconds

		//force interval update
		forceIntervalUpdate(intervalName);

		assertEquals(1, consumer.getConsumedCount());

		SnapshotRepository.getInstance().removeConsumer(consumer);
		//force interval update
		forceIntervalUpdate(intervalName);

		assertEquals(1, consumer.getConsumedCount());

	}

	private static class MySnapshotConsumer implements SnapshotConsumer{

		private int consumedCount = 0;

		@Override
		public void consumeSnapshot(ProducerSnapshot snapshot) {
			consumedCount++;
			System.out.println("CONSUMED "+snapshot);
		}

		public int getConsumedCount(){
			return consumedCount;
		}

		@Override public String toString(){
			return getClass().getSimpleName()+" "+getConsumedCount();
		}

		@Override public boolean equals(Object o){
			return o == this;
		}
	}
}

