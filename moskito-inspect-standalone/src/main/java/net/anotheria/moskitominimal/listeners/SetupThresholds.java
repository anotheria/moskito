package net.anotheria.moskitominimal.listeners;

import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.core.threshold.Thresholds;
import net.anotheria.moskito.core.threshold.guard.GuardedDirection;
import net.anotheria.moskito.core.threshold.guard.LongBarrierPassGuard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Don't use this listener anymore, use moskito.json to configure thresholds.
 * Example: https://github.com/anotheria/moskito/blob/master/moskito-inspect-standalone/src/main/resources/moskito.json
 */
@Deprecated
public class SetupThresholds implements ServletContextListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(SetupThresholds.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		LOGGER.info("Configuring thresholds ... ");
		setupMemory();
		setupThreadCount();
		LOGGER.info(" ... done.");
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
