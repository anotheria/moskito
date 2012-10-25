package net.anotheria.moskito.core.treshold;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.GuardConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test for the configuration of the
 *
 * @author lrosenberg
 * @since 25.10.12 10:53
 */
public class ThresholdConfigTest {
	@BeforeClass
	public static void setup(){
		System.setProperty("JUNITTEST", "false");
		ProducerRegistryFactory.reset();

	}

	@AfterClass
	public static void teardown(){
		System.setProperty("JUNITTEST", "true");
		ProducerRegistryFactory.reset();
	}

	@Before
	public void prepareConfiguration(){
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		ThresholdsConfig tc = new ThresholdsConfig();
		ThresholdConfig[] arr = new ThresholdConfig[2];

		ThresholdConfig c1 = new ThresholdConfig();
		c1.setName("ThresholdConfigTest-FIRST");
		c1.setIntervalName("snapshot");
		c1.setProducerName("ThresholdConfigTest");
		c1.setStatName("first");
		c1.setValueName("REQ");
		c1.setIntervalName("snapshot");
		c1.setTimeUnit(TimeUnit.MILLISECONDS.name());

		c1.setGuards(new GuardConfig[]{
			new GuardConfig("GREEN", "DOWN", "50"),
			new GuardConfig("YELLOW", "UP", "50"),
			new GuardConfig("ORANGE", "UP", "90"),
			new GuardConfig("RED", "UP", "110"),
			new GuardConfig("PURPLE", "UP", "150")
		});

		ThresholdConfig c2 = new ThresholdConfig();
		c2.setName("ThresholdConfigTest-SECOND");
		c2.setIntervalName("snapshot");
		c2.setProducerName("ThresholdConfigTest");
		c2.setStatName("second");
		c2.setValueName("TIME");
		c2.setIntervalName("5m");
		c2.setTimeUnit(TimeUnit.NANOSECONDS.name());

		arr[0] = c1;
		arr[1] = c2;
		tc.setThresholds(arr);
		config.setThresholdsConfig(tc);

		MoskitoConfigurationHolder.INSTANCE.setConfiguration(config);
		ThresholdRepository.getInstance().reset();

	}

	@Test public void findConfiguredThreshold(){
		Threshold first = ThresholdRepository.getInstance().getByName("ThresholdConfigTest-FIRST");
		Threshold second = ThresholdRepository.getInstance().getByName("ThresholdConfigTest-SECOND");
		assertNotNull(first);
		assertNotNull(second);
		assertNull(first.getStats());

		assertEquals("ThresholdConfigTest", first.getDefinition().getProducerName());
		assertEquals("snapshot", first.getDefinition().getIntervalName());
		assertEquals("first", first.getDefinition().getStatName());
		assertEquals("REQ", first.getDefinition().getValueName());
		assertEquals(TimeUnit.MILLISECONDS, first.getDefinition().getTimeUnit());

		//now create stats object and check that if will be connected.
		DummyStatProducer p = new DummyStatProducer("ThresholdConfigTest");

		assertNotNull(second);
		assertNotNull(second.getStats());
		assertNotNull(first);
		assertNotNull(first.getStats());

		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		assertEquals(ThresholdStatus.ORANGE, first.getStatus());
		assertEquals("100", first.getLastValue());

	}

	@After
	public void resetConfiguration(){
		MoskitoConfigurationHolder.resetConfiguration();
	}


}
