package net.java.dev.moskitodemo.threshold.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdDefinition;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.core.treshold.ThresholdStatus;
import net.java.dev.moskito.core.treshold.guard.DoubleBarrierPassGuard;
import net.java.dev.moskito.core.treshold.guard.GuardedDirection;
import net.java.dev.moskito.core.treshold.guard.LongBarrierPassGuard;

public class SetupThresholds implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		setupServiceTRThreshold();
		setupServiceAVGThreshold();
		setupRequestURIThreshold();
		setupMemory();


		///
		
	}
	
	private void setupServiceTRThreshold(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardedService-1");
		config.setStatName("guardedMethod");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		config.setName("GuardedService TotalRequest");
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.YELLOW, 100, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.ORANGE, 200, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.RED, 500, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.PURPLE, 1000, GuardedDirection.UP));
	}
	
	private void setupServiceAVGThreshold(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardedService-1");
		config.setStatName("guardedAverageMethod");
		config.setValueName("AVG");
		config.setIntervalName("snapshot");
		config.setName("GuardedService AVG");
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		threshold.addGuard(new DoubleBarrierPassGuard(ThresholdStatus.GREEN, 1000*1.0, GuardedDirection.UP));
		threshold.addGuard(new DoubleBarrierPassGuard(ThresholdStatus.YELLOW, 1000*2.0, GuardedDirection.UP));
		threshold.addGuard(new DoubleBarrierPassGuard(ThresholdStatus.ORANGE, 1000*3.0, GuardedDirection.UP));
		threshold.addGuard(new DoubleBarrierPassGuard(ThresholdStatus.RED, 1000*4.0, GuardedDirection.UP));
		threshold.addGuard(new DoubleBarrierPassGuard(ThresholdStatus.PURPLE, 1000*5.0, GuardedDirection.UP));
	}

	private void setupRequestURIThreshold(){
		
		// /moskitodemo/guestbook/gbookShowComments 		
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("RequestURIFilter");
		config.setStatName("/moskitodemo/guestbook/gbookShowComments");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		config.setName("Guestbook");
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.YELLOW, 10, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.ORANGE, 20, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.RED, 50, GuardedDirection.UP));
		threshold.addGuard(new LongBarrierPassGuard(ThresholdStatus.PURPLE, 100, GuardedDirection.UP));
	}
	
	private void setupMemory() {
		setupMemoryThreshold("PermGenFree", "MemoryPool-PS Perm Gen-NonHeap", "Free", 
			new LongBarrierPassGuard(ThresholdStatus.GREEN, 1000 * 1000 * 5, GuardedDirection.UP), /* */
			new LongBarrierPassGuard(ThresholdStatus.YELLOW, 1000 * 1000 * 5, GuardedDirection.DOWN), /* */
			new LongBarrierPassGuard(ThresholdStatus.ORANGE, 1000 * 1000 * 2, GuardedDirection.DOWN), /* */
			new LongBarrierPassGuard(ThresholdStatus.RED, 1000 * 1000 * 1, GuardedDirection.DOWN), /* */
			new LongBarrierPassGuard(ThresholdStatus.PURPLE, 1000 * 1, GuardedDirection.DOWN) /* */
		);

		setupMemoryThreshold("OldGenFree", "MemoryPool-PS Old Gen-Heap", "Free", /* */
		new LongBarrierPassGuard(ThresholdStatus.GREEN, 1000 * 1000 * 100, GuardedDirection.UP), /* */
		new LongBarrierPassGuard(ThresholdStatus.YELLOW, 1000 * 1000 * 50, GuardedDirection.DOWN), /* */
		new LongBarrierPassGuard(ThresholdStatus.ORANGE, 1000 * 1000 * 10, GuardedDirection.DOWN), /* */
		new LongBarrierPassGuard(ThresholdStatus.RED, 1000 * 1000 * 2, GuardedDirection.DOWN), /* */
		new LongBarrierPassGuard(ThresholdStatus.PURPLE, 1000 * 1000 * 1, GuardedDirection.DOWN) /* */
		);
	}

	private void setupMemoryThreshold(String name, String producerName, String valueName, ThresholdConditionGuard... guards) {
		setup(name, producerName, producerName, valueName, "1m", guards);
	}

	private void setupUrlAVG(String name, String url, ThresholdConditionGuard... guards) {
		setup(name, "RequestURIFilter", url, "AVG", "5m", guards);
	}

	private void setupServiceAVG(String name, String producerName, ThresholdConditionGuard... guards) {
		setup(name, producerName, "cumulated", "AVG", "5m", guards);
	}

	private void setup(String name, String producerName, String statName, String valueName, String intervalName,
			ThresholdConditionGuard... guards) {
		ThresholdDefinition definition = new ThresholdDefinition();
		definition.setName(name);
		definition.setProducerName(producerName);
		definition.setStatName(statName);
		definition.setValueName(valueName);
		definition.setIntervalName(intervalName);

		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(definition);
		if (guards != null) {
			for (ThresholdConditionGuard g: guards) {
				threshold.addGuard(g);
			}
		}
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
