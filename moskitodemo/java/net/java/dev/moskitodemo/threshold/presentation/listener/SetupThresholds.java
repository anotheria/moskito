package net.java.dev.moskitodemo.threshold.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdDefinition;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.core.treshold.ThresholdStatus;
import net.java.dev.moskito.core.treshold.guard.BarrierPassGuard;
import net.java.dev.moskito.core.treshold.guard.GuardedDirection;
import net.java.dev.moskitodemo.threshold.presentation.action.EmulateRequestsAction;

public class SetupThresholds implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		setupServiceThreshold();
		setupRequestURIThreshold();


		///
		
	}
	
	private void setupServiceThreshold(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardedService-1");
		config.setStatName("guardedMethod");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		config.setName("GuardedService TotalRequest");
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.YELLOW, 100, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.ORANGE, 200, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.RED, 500, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.PURPLE, 1000, GuardedDirection.UP));
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
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.GREEN, 1, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.YELLOW, 10, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.ORANGE, 20, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.RED, 50, GuardedDirection.UP));
		threshold.addGuard(new BarrierPassGuard(ThresholdStatus.PURPLE, 100, GuardedDirection.UP));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
