package net.java.dev.moskito.webui.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.accumulation.Accumulators;

import org.apache.log4j.Logger;

public class SetupPreconfiguredAccumulators implements ServletContextListener{
	
	private static Logger log = Logger.getLogger(SetupPreconfiguredAccumulators.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("Configuring memory accumulators.");
		setupMemoryAccumulators();
		log.info("Configuring thread accumulators.");
		setupThreadAccumulators();
		log.info("Configuring session accumulators.");
		setupSessionCountAccumulators();
	}
	
	private static void setupThreadAccumulators(){
		Accumulators.createAccumulator("ThreadCount", "ThreadCount", "ThreadCount", "current", "default");
	}
	
	private static void setupSessionCountAccumulators(){
		Accumulators.createAccumulator("SessionCount CurAbsolute", "SessionCount", "Sessions", "cur", "default");
		Accumulators.createAccumulator("SessionCount Cur1h", "SessionCount", "Sessions", "cur", "1h");
		Accumulators.createAccumulator("SessionCount New1h", "SessionCount", "Sessions", "new", "1h");
		Accumulators.createAccumulator("SessionCount Del1h", "SessionCount", "Sessions", "del", "1h");
	}
	
	
	public static void setupMemoryAccumulators(){
		Accumulators.createMemoryAccumulator1m("PermGenFree 1m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator1m("PermGenFree MB 1m", "MemoryPool-PS Perm Gen-NonHeap", "Free MB"); 
		Accumulators.createMemoryAccumulator1m("OldGenFree 1m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator1m("OldGenFree MB 1m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator1m("OldGenUsed 1m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator1m("OldGenUsed MB 1m", "MemoryPool-PS Old Gen-Heap", "Used MB");

		Accumulators.createMemoryAccumulator5m("PermGenFree 5m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator5m("PermGenFree MB 5m", "MemoryPool-PS Perm Gen-NonHeap", "Free MB"); 
		Accumulators.createMemoryAccumulator5m("OldGenFree 5m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator5m("OldGenFree MB 5m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator5m("OldGenUsed 5m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator5m("OldGenUsed MB 5m", "MemoryPool-PS Old Gen-Heap", "Used MB");
	
		Accumulators.createMemoryAccumulator("PermGenFree 1h", "MemoryPool-PS Perm Gen-NonHeap", "Free", "1h"); 
		Accumulators.createMemoryAccumulator("PermGenFree MB 1h", "MemoryPool-PS Perm Gen-NonHeap", "Free MB", "1h"); 
		Accumulators.createMemoryAccumulator("OldGenFree 1h", "MemoryPool-PS Old Gen-Heap", "Free", "1h");
		Accumulators.createMemoryAccumulator("OldGenFree MB 1h", "MemoryPool-PS Old Gen-Heap", "Free MB", "1h");
		Accumulators.createMemoryAccumulator("OldGenUsed 1h", "MemoryPool-PS Old Gen-Heap", "Used", "1h");
		Accumulators.createMemoryAccumulator("OldGenUsed MB 1h", "MemoryPool-PS Old Gen-Heap", "Used MB", "1h");
	
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
 