package net.java.dev.moskitodemo.threshold.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.treshold.ThresholdDefinition;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskitodemo.threshold.presentation.action.EmulateRequestsAction;

public class SetupThresholds implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//force producer to load.
		//String nothing = ""+EmulateRequestsAction.class.getName();
		//EmulateRequestsAction.service.guardedMethod();
		
		
		System.out.println(new ProducerRegistryAPIFactory().createProducerRegistryAPI().getAllProducers());
		
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("GuardedService-1");
		config.setStatName("guardedMethod");
		config.setValueName("TR");
		config.setIntervalName("snapshot");
		
		ThresholdRepository.INSTANCE.createThreshold(config);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
