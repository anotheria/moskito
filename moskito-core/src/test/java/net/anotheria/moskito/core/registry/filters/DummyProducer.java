package net.anotheria.moskito.core.registry.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

import java.util.List;


public class DummyProducer implements IStatsProducer{
	private String id;
	private String category;
	private String subsystem;

	public DummyProducer (String anId, String aCategory, String aSubsystem){
		id = anId;
		category = aCategory;
		subsystem = aSubsystem;
	}


	@Override
	public List getStats() {
		return null;
	}

	@Override
	public String getProducerId() {
		return id;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getSubsystem() {
		return subsystem;
	}
}
