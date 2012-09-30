package net.anotheria.moskito.core.treshold;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;


public class FindThresholdTest {
	@BeforeClass public static void setup(){
		System.setProperty("JUNITTEST", "false");
		ProducerRegistryFactory.reset();
	}
	
	@AfterClass public static void teardown(){
		System.setProperty("JUNITTEST", "true");
		ProducerRegistryFactory.reset();
	}

	@Test public void findUsed(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("Heap memory");
		config.setStatName("Heap memory");
		config.setValueName("USED");
		config.setIntervalName(null);
		
		IProducerRegistryAPI registryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer<?> producer = registryAPI.getProducer(config.getProducerName());
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
	
	@Test public void findOldGen(){
		ThresholdDefinition def = new ThresholdDefinition();
		def.setProducerName("MemoryPool-PS Old Gen-Heap");
		def.setStatName("MemoryPool-PS Old Gen-Heap");
		def.setValueName("FREE");
		def.setIntervalName("snapshot");
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(def);
		assertNotNull(threshold);
		
		assertEquals("none yet", threshold.getLastValue());
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		System.out.println(threshold.getLastValue());
		
	}
	
	@Test public void findFreeViaRegistry(){
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("Heap memory");
		config.setStatName("Heap memory");
		config.setValueName("FREE");
		config.setIntervalName(null);
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
		assertNotNull(threshold);
		
		assertEquals("none yet", threshold.getLastValue());
		
		
	}
	@Test public void findDummyViaRegistry(){
		new DummyStatProducer("dummy");
		ThresholdDefinition config = new ThresholdDefinition();
		config.setProducerName("dummy");
		config.setStatName("third");
		config.setValueName("TR");
		config.setIntervalName(null);
		
		Threshold threshold = ThresholdRepository.getInstance().createThreshold(config);
		assertNotNull(threshold);
		
		assertEquals("none yet", threshold.getLastValue());
		
		threshold.update();
		assertEquals("300", threshold.getLastValue());
		
		
		
	}
}
