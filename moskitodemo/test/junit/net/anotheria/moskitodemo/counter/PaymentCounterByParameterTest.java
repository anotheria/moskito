package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 09:54
 */
public class PaymentCounterByParameterTest {
	@Test
	public void testDifferentPaymentMethods() throws OnDemandStatsProducerException {

		PaymentCounterByParameter counter = new PaymentCounterByParameter();

		//now make different payments
		counter.pay("ec", true);
		counter.pay("ec", true);
		counter.pay("cc", true);
		counter.pay("paypal", true);

		//and this payment failed
		counter.pay("cc", false);

		//As you see we had a total of 4 payments, one credit card payment, 2 ec payments and one paypal. Let's test it:
		//this code is just to test the results, you don't need to repeat it in your code, the counters will show up in the webui.
		IProducerRegistryAPI registry = new ProducerRegistryAPIFactory().createProducerRegistryAPI();

		//check successes
		OnDemandStatsProducer<CounterStats> successProducer = (OnDemandStatsProducer<CounterStats>)registry.getProducer("PaymentCounterByParameter.success");
		assertNotNull(successProducer);
		assertEquals(4, successProducer.getDefaultStats().get());
		assertEquals(2, successProducer.getStats("ec").get());
		assertEquals(1, successProducer.getStats("cc").get());
		assertEquals(1, successProducer.getStats("paypal").get());

		//check failures
		OnDemandStatsProducer<CounterStats> failureProducer = (OnDemandStatsProducer<CounterStats>)registry.getProducer("PaymentCounterByParameter.failure");
		assertNotNull(failureProducer);
		assertEquals(1, failureProducer.getDefaultStats().get());
		assertEquals(0, failureProducer.getStats("ec").get());
		assertEquals(1, failureProducer.getStats("cc").get());
		assertEquals(0, failureProducer.getStats("paypal").get());


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
