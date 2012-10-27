package net.anotheria.moskito.core.threshold;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.util.ArrayList;
import java.util.List;

public class DummyStatProducer implements IStatsProducer {

	private List<IStats> stats = new ArrayList<IStats>();
	ServiceStats dynamic;
	private String producerId = "dummy";
	
	public DummyStatProducer(String aProducerId){
		producerId = aProducerId;
		ServiceStats stat1 = new ServiceStats("first");
		ServiceStats stat2 = new ServiceStats("second");
		ServiceStats stat3 = new ServiceStats("third");
		ServiceStats stat4 = new ServiceStats("dynamic");
		dynamic = stat4;
		
		for (int i=0; i<100; i++){
			stat1.addRequest();
			stat2.addRequest();stat2.addRequest();
			stat3.addRequest();stat3.addRequest();stat3.addRequest();
		}
		
		stats.add(stat1);stats.add(stat2);stats.add(stat3);stats.add(stat4);

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);

	}
	
	@Override
	public List<IStats> getStats() {
		return stats;
	}

	@Override
	public String getProducerId() {
		return producerId;
	}

	@Override
	public String getCategory() {
		return "default";
	}

	@Override
	public String getSubsystem() {
		return "default";
	}

	public void increaseDynamic(){
		dynamic.addRequest();
	}
	
	public long getDynamicTR(String intervalName){
		return dynamic.getTotalRequests(intervalName);
	}
}
