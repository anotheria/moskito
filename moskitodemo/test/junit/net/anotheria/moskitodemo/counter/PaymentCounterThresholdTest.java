package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.11.12 21:30
 */
public class PaymentCounterThresholdTest {
	@Test
	public void testDifferentPaymentMethods() throws OnDemandStatsProducerException {

		PaymentCounter counter = new PaymentCounter();
		//now make different payments
		counter.ec();
		counter.ec();
		counter.cc();
		counter.paypal();

		ThresholdDefinition td = new ThresholdDefinition();
		td.setName("ILOVECOUNTERS");
		td.setProducerName(PaymentCounter.class.getSimpleName());
		td.setIntervalName("1m");
		td.setStatName("cc");
		td.setValueName("counter");

		Threshold tt = ThresholdRepository.getInstance().createThreshold(td);
		tt.addLongGuardLineDownUp(5,5, 10, 15, 20);
		System.out.println(tt);

		//fire the threshold
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");
		assertEquals(ThresholdStatus.GREEN, tt.getStatus());

		//force next update
		counter.cc(); counter.cc(); counter.cc();
		counter.cc(); counter.cc(); counter.cc();

		//fire the threshold
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");
		assertEquals(ThresholdStatus.YELLOW, tt.getStatus());




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
