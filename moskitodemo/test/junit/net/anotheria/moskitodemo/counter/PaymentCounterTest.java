package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * This test illustrates how easy it is to count something with moskito and @Count anotation.
 *
 * @author lrosenberg
 * @since 19.11.12 09:18
 */
public class PaymentCounterTest {
	@Test public void testDifferentPaymentMethods() throws OnDemandStatsProducerException{

		PaymentCounter counter = new PaymentCounter();

		//now make different payments
		counter.ec();
		counter.ec();
		counter.cc();
		counter.paypal();

		//As you see we had a total of 4 payments, one credit card payment, 2 ec payments and one paypal. Let's test it:
		//this code is just to test the results, you don't need to repeat it in your code, the counters will show up in the webui.
		IProducerRegistryAPI registry = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer<CounterStats> producer = (IStatsProducer<CounterStats>)registry.getProducer("PaymentCounter");
		assertNotNull(producer);
		//total stats is always at first place in producer which is an instance of an on-demand-producer.
		assertEquals(4, producer.getStats().get(0).get());
		//other stats follow in order they are first called.
		assertEquals(2, producer.getStats().get(1).get());
		assertEquals("ec", producer.getStats().get(1).getName());

		assertEquals(1, producer.getStats().get(2).get());
		assertEquals("cc", producer.getStats().get(2).getName());

		assertEquals(1, producer.getStats().get(3).get());
		assertEquals("paypal", producer.getStats().get(3).getName());

		//we can achieve same tests (if we wanted) by using ondemandproducer directly:
		OnDemandStatsProducer<CounterStats> onDemandProducer = (OnDemandStatsProducer<CounterStats>)producer;
		assertEquals(4, onDemandProducer.getDefaultStats().get());
		assertEquals(2, onDemandProducer.getStats("ec").get());
		assertEquals(1, onDemandProducer.getStats("cc").get());
		assertEquals(1, onDemandProducer.getStats("paypal").get());

	}

	@BeforeClass
	public static void setup(){
		//disable builtin producers
		System.setProperty("JUNITTEST", Boolean.TRUE.toString());
	}

	@AfterClass
	public static void cleanup(){
		ProducerRegistryFactory.reset();
	}


}
