package net.java.dev.listener.moskitominimal.listeners;

import net.anotheria.moskito.core.treshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.treshold.ThresholdStatus;
import net.anotheria.moskito.core.treshold.Thresholds;
import net.anotheria.moskito.core.treshold.guard.GuardedDirection;
import net.anotheria.moskito.core.treshold.guard.LongBarrierPassGuard;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SetupThresholds implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		System.out.println("Configuring thresholds ... ");
		setupMemory();
		setupThreadCount();
		System.out.println(" ... done.");
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

	private void setupThreadCount() {
		Thresholds.addThreshold("ThreadCount", "ThreadCount", "ThreadCount", "Current", "default", new LongBarrierPassGuard(ThresholdStatus.GREEN, 200, GuardedDirection.DOWN),
				new LongBarrierPassGuard(ThresholdStatus.YELLOW, 200, GuardedDirection.UP), new LongBarrierPassGuard(ThresholdStatus.ORANGE, 300, GuardedDirection.UP),
				new LongBarrierPassGuard(ThresholdStatus.RED, 500, GuardedDirection.UP), new LongBarrierPassGuard(ThresholdStatus.PURPLE, 1000, GuardedDirection.UP));
	}



	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
