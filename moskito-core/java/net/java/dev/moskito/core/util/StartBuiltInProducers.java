package net.java.dev.moskito.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

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
		startOsProducer();
	}
	
	public static void restartbuiltin(){
		initialized = false;
		startbuiltin();
	}

	private static void startJavaThreadingProducers(){
		new BuiltInThreadCountProducer();
	}
	
	private static void startOsProducer(){
		new BuiltInOSProducer();		
	}
	
	private static void startJavaMemoryProducers(){
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.FREE));
		registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.MAX));
		registry.registerProducer(new BuiltInMemoryProducer(BuiltInMemoryProducer.TOTAL));
		
		HashMap<MemoryType, List<BuiltInMemoryPoolProducer>> producers = new HashMap<MemoryType, List<BuiltInMemoryPoolProducer>>();
		
		List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean pool : pools){
			BuiltInMemoryPoolProducer p = new BuiltInMemoryPoolProducer(pool); 
			registry.registerProducer(p);
			List<BuiltInMemoryPoolProducer> pp = producers.get(pool.getType());
			if (pp==null){
				pp = new ArrayList<BuiltInMemoryPoolProducer>();
				producers.put(pool.getType(), pp);
			}
			pp.add(p);
		}
		
		//now finally add virtual producers
		for (MemoryType t : producers.keySet()){
			BuiltInMemoryPoolVirtualProducer vp = new BuiltInMemoryPoolVirtualProducer(t, producers.get(t));
			registry.registerProducer(vp);
		}
		
	}
}
