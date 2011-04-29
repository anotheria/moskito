package net.java.dev.moskitodemo.threshold.presentation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.java.dev.moskito.core.accumulation.Accumulators;
import net.java.dev.moskitodemo.accumulator.RandomProducer;

public class SetupAccumulators implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		//start demo data
		new RandomProducer(10, 100);
		new RandomProducer(100, 100);
		new RandomProducer(1000, 1000);
		
		System.out.println("Configuring accumulators ... ");
		setupMemoryAccumulators();
		setupRandomServiceAccumulators();
		System.out.println(" ... done.");
	}
	
	private static void setupRandomServiceAccumulators(){
		Accumulators.createServiceREQAccumulator("Random1-REQ-5m", "Random1", "5m");
		Accumulators.createServiceREQAccumulator("Random2-REQ-5m", "Random2", "5m");
		Accumulators.createServiceREQAccumulator("Random3-REQ-5m", "Random3", "5m");

		Accumulators.createServiceREQAccumulator("Random1-REQ-1m", "Random1", "1m");
		Accumulators.createServiceREQAccumulator("Random2-REQ-1m", "Random2", "1m");
		Accumulators.createServiceREQAccumulator("Random3-REQ-1m", "Random3", "1m");

		Accumulators.createServiceAVGAccumulator("Random1-AVG-5m", "Random1", "5m");
		Accumulators.createServiceAVGAccumulator("Random2-AVG-5m", "Random2", "5m");
		Accumulators.createServiceAVGAccumulator("Random3-AVG-5m", "Random3", "5m");

		Accumulators.createServiceAVGAccumulator("Random1-AVG-1m", "Random1", "1m");
		Accumulators.createServiceAVGAccumulator("Random2-AVG-1m", "Random2", "1m");
		Accumulators.createServiceAVGAccumulator("Random3-AVG-1m", "Random3", "1m");
	}
	
	public static void setupMemoryAccumulators(){
		Accumulators.createMemoryAccumulator1m("PermGenFree 1m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator1m("OldGenFree 1m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator1m("OldGenFree MB 1m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator1m("OldGenUsed 1m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator1m("OldGenUsed MB 1m", "MemoryPool-PS Old Gen-Heap", "Used MB");

		Accumulators.createMemoryAccumulator5m("PermGenFree 5m", "MemoryPool-PS Perm Gen-NonHeap", "Free"); 
		Accumulators.createMemoryAccumulator5m("OldGenFree 5m", "MemoryPool-PS Old Gen-Heap", "Free");
		Accumulators.createMemoryAccumulator5m("OldGenFree MB 5m", "MemoryPool-PS Old Gen-Heap", "Free MB");
		Accumulators.createMemoryAccumulator5m("OldGenUsed 5m", "MemoryPool-PS Old Gen-Heap", "Used");
		Accumulators.createMemoryAccumulator5m("OldGenUsed MB 5m", "MemoryPool-PS Old Gen-Heap", "Used MB");
}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
 