package net.anotheria.moskitodemo.threshold.presentation.listener;

import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdDefinition;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.Thresholds;
import net.anotheria.moskito.core.threshold.guard.DoubleBarrierPassGuard;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetupThresholds implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		System.out.println("Configuring thresholds ... ");
		setupServiceTRThreshold();
		setupServiceAVGThreshold();
		setupRequestURIThreshold();
		setupMemory();
		System.out.println(" ... done.");
	}
	
	private void setupServiceTRThreshold(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardedService-1");
		config.setStatName("guardedMethod");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		config.setName("GuardedService TotalRequest");
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
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
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
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
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
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
		Thresholds.addMemoryThreshold(name, producerName, valueName, guards);
	}

	private void setupUrlAVG(String name, String url, ThresholdConditionGuard... guards) {
		Thresholds.addUrlAVGThreshold(name, url, guards);
	}

	 
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
