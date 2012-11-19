package net.anotheria.moskitodemo.guestbook.business;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 01:03
 */
public class BusinessCommentCounterTest {
	@BeforeClass public static void setup(){
		//disable builtin producers
		System.setProperty("JUNITTEST", Boolean.TRUE.toString());
	}

	@Test public void test(){
		BusinessCommentCounter counter = new BusinessCommentCounter();
		counter.created();
		counter.created();
		counter.created();
		counter.deleted();

		//now check moskito
		IProducerRegistryAPI registry = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer<CounterStats> producer = registry.getProducer("BusinessCommentCounter");
		//total of 4 calls
		assertEquals(4L, producer.getStats().get(0).get());

		assertEquals(3L, producer.getStats().get(1).get());
		assertEquals("created", producer.getStats().get(1).getName());

		assertEquals(1L, producer.getStats().get(2).get());
		assertEquals("deleted", producer.getStats().get(2).getName());
	}

	@AfterClass public static void cleanup(){
		ProducerRegistryFactory.reset();
	}
}
