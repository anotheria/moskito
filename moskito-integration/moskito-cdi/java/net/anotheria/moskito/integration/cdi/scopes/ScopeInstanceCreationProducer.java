package net.anotheria.moskito.integration.cdi.scopes;

import net.anotheria.moskito.core.producers.IStatsProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.11.13 17:02
 */
public class ScopeInstanceCreationProducer implements IStatsProducer<ScopeCountStats>{

	private String id;
	private String category;
	private String subsystem;
	private ArrayList<ScopeCountStats> stats;

	public ScopeInstanceCreationProducer(String anId, ScopeCountStats statsObject){
		this(anId, "cdi", "builtin", statsObject);
	}

	public ScopeInstanceCreationProducer(String anId, String aCategory, String aSubsystem, ScopeCountStats statsObject){
		id = anId;
		category = aCategory;
		subsystem = aSubsystem;
		stats = new ArrayList<ScopeCountStats>(1);
		stats.add(statsObject);
	}

	@Override
	public List<ScopeCountStats> getStats() {
		return stats;
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
