package net.java.dev.moskito.core.treshold;

import static org.junit.Assert.*;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;

import org.junit.Test;

public class FindThresholdTest {
	@Test public void findUsed(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("Heap memory");
		config.setStatName("Heap memory");
		config.setValueName("USED");
		config.setIntervalName(null);
		
		IProducerRegistryAPI registryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer producer = registryAPI.getProducer(config.getProducerName());
		assertNotNull(producer);
		
		IStats target = null;
		for (IStats s : producer.getStats()){
			if (s.getName().equals(config.getStatName())){
				target = s;
				break;
			}
		}
		
		assertNotNull(target);
		
		String value = target.getValueByNameAsString(config.getValueName(), null, null);
		assertNotNull(value);
		//System.out.println(value);
	}
	
	@Test public void findFreeViaRegistry(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("Heap memory");
		config.setStatName("Heap memory");
		config.setValueName("FREE");
		config.setIntervalName(null);
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		assertNotNull(threshold);
		
		assertEquals("none yet", threshold.getLastValue());
		
		
	}
	@Test public void findDummyViaRegistry(){
		new DummyStatProducer();
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("dummy");
		config.setStatName("third");
		config.setValueName("TR");
		config.setIntervalName(null);
		
		Threshold threshold = ThresholdRepository.INSTANCE.createThreshold(config);
		assertNotNull(threshold);
		
		assertEquals("none yet", threshold.getLastValue());
		
		threshold.update();
		assertEquals("300", threshold.getLastValue());
		
		
		
	}
}
