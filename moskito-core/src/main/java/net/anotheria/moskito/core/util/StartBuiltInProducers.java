package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * A utility for starting built-in producers upon moskito start / initialization.
 * @author lrosenberg
 *
 */
public class StartBuiltInProducers {
	private static volatile boolean initialized = false;
	
	public static synchronized void startbuiltin(){
		if (initialized)
			return;
		initialized = true;

		startJavaMemoryProducers();
		startJavaThreadingProducers();
		startOsProducers();
		startMBeanProducers();
	}
	
	public static void restartbuiltin(){
		initialized = false;
		startbuiltin();
	}

	private static void startJavaThreadingProducers(){
		if (!MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isJavaThreadingProducers())
			return;
		new BuiltInThreadStatesProducer();
		new BuiltInThreadCountProducer();
	}
	
	private static void startOsProducers(){
		if (MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isOsProducer())
			new BuiltInOSProducer();
		if (MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isRuntimeProducer())
			new BuiltInRuntimeProducer();
	}
	
	private static void startJavaMemoryProducers(){
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		if (MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isJavaMemoryProducers()){
			registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.FREE));
			registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.MAX));
			registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.TOTAL));
		}

		if (MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().isJavaMemoryPoolProducers()){
			Map<MemoryType, List<BuiltInMemoryPoolProducer>> producers = new EnumMap<>(MemoryType.class);

			List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
			for (MemoryPoolMXBean pool : pools){
				BuiltInMemoryPoolProducer p = new BuiltInMemoryPoolProducer(pool);
				registry.registerProducer(p);
				List<BuiltInMemoryPoolProducer> pp = producers.get(pool.getType());
				if (pp==null){
					pp = new ArrayList<>(pools.size());
					producers.put(pool.getType(), pp);
				}
				pp.add(p);
			}

			//now finally add virtual producers
			for (Map.Entry<MemoryType,List<BuiltInMemoryPoolProducer>> t : producers.entrySet()){
				IStatsProducer vp = new BuiltInMemoryPoolVirtualProducer(t.getKey(), t.getValue());
				registry.registerProducer(vp);
			}
		}
		
	}
	
	/**
	 * Checks if generic MBeanProducers are enabled and creates them if desired.
	 */
	private static void startMBeanProducers() {
	    if (MoskitoConfigurationHolder.getConfiguration().getMbeanProducersConfig().isRegisterAutomatically()) {
	        MBeanProducerFactory.buildProducers();
	    }
	}
}
