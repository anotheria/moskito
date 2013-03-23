package net.anotheria.moskito.central;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.plugins.PluginRepository;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.snapshot.SnapshotRepository;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 15:04
 */
public class CentralIntegrationTest {
	@Before
	public void setup(){
		System.setProperty("JUNITTEST", "true");
		ProducerRegistryFactory.reset();
		//this only needed because in test mode we don't call it automagically.
		PluginRepository.getInstance();

	}

	@Test
	public void testIntegration() throws Exception{

		System.out.println("Plugins config:" +MoskitoConfigurationHolder.getConfiguration().getPluginsConfig());
		System.out.println("plugins: "+ Arrays.asList(MoskitoConfigurationHolder.getConfiguration().getPluginsConfig().getPlugins()));
		SnapshotRepository.getInstance();
		System.out.println("Yes... "+SnapshotRepository.getInstance());

		String intervalName = "5m";
		//force interval update
		forceIntervalUpdate(intervalName);

		OnDemandStatsProducer<ServiceStats> producer = setupProducer();
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);

		//add first stat.
		ServiceStats stat1 =  producer.getStats("case1");
		stat1.addRequest();
		stat1.addExecutionTime(100); //100 milliseconds

		//force interval update
		forceIntervalUpdate(intervalName);

		//force interval update
		forceIntervalUpdate(intervalName);

		System.out.println("FIRED");
		Thread.sleep(500);


	}

	protected static OnDemandStatsProducer setupProducer(){
		OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<ServiceStats>("testProducerId", "aCategory", "aSubsystem", new ServiceStatsFactory());
		return producer;
	}

	protected static void forceIntervalUpdate(String intervalName){
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
	}

}
