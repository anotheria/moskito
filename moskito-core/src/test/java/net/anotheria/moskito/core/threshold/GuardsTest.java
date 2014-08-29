package net.anotheria.moskito.core.threshold;

import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class GuardsTest {
	@Test public void testStatusChange(){
		DummyStatProducer producer = new DummyStatProducer("GuardsTest");
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardsTest");
		config.setStatName("dynamic");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		config.setName("GuardsTest");
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
		assertNotNull(threshold);
		assertEquals("none yet", threshold.getLastValue());
		
		//manually add guards
		
		//first request switches the guard on.
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.GREEN, 100, GuardedDirection.DOWN));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.YELLOW, 100, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.ORANGE, 200, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.RED, 500, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.PURPLE, 1000, GuardedDirection.UP));

		//start testing
		forceStatusChangeAndCheckIt(producer, threshold, 1, ThresholdStatus.GREEN);
		forceStatusChangeAndCheckIt(producer, threshold, 5, ThresholdStatus.GREEN);
		forceStatusChangeAndCheckIt(producer, threshold, 99, ThresholdStatus.GREEN);

		forceStatusChangeAndCheckIt(producer, threshold, 150, ThresholdStatus.YELLOW);
		forceStatusChangeAndCheckIt(producer, threshold, 99, ThresholdStatus.GREEN);
		
		forceStatusChangeAndCheckIt(producer, threshold, 199, ThresholdStatus.YELLOW);
		forceStatusChangeAndCheckIt(producer, threshold, 200, ThresholdStatus.ORANGE);
		forceStatusChangeAndCheckIt(producer, threshold, 500, ThresholdStatus.RED);
		forceStatusChangeAndCheckIt(producer, threshold, 1000, ThresholdStatus.PURPLE);

		forceStatusChangeAndCheckIt(producer, threshold, 150, ThresholdStatus.YELLOW);
		forceStatusChangeAndCheckIt(producer, threshold, 99, ThresholdStatus.GREEN);
		
	}
	
	private void forceStatusChangeAndCheckIt(DummyStatProducer producer, Threshold threshold, int numberOfCalls, ThresholdStatus expectedStatus){
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		
		for (int i=0; i<numberOfCalls; i++)
			producer.increaseDynamic();
		
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		assertEquals(expectedStatus, threshold.getStatus());
	}
}
