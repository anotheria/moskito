package net.java.dev.moskito.core.treshold;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class DummyStatProducer implements IStatsProducer{

	private List<IStats> stats = new ArrayList<IStats>();
	
	public DummyStatProducer(){
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
		ServiceStats stat1 = new ServiceStats("first");
		ServiceStats stat2 = new ServiceStats("second");
		ServiceStats stat3 = new ServiceStats("third");
		
		for (int i=0; i<100; i++){
			stat1.addRequest();
			stat2.addRequest();stat2.addRequest();
			stat3.addRequest();stat3.addRequest();stat3.addRequest();
		}
		
		stats.add(stat1);stats.add(stat2);stats.add(stat3);
	}
	
	@Override
	public List<IStats> getStats() {
		return stats;
	}

	@Override
	public String getProducerId() {
		return "dummy";
	}

	@Override
	public String getCategory() {
		return "default";
	}

	@Override
	public String getSubsystem() {
		return "default";
	}
	
}
