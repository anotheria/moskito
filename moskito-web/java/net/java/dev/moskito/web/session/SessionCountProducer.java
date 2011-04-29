package net.java.dev.moskito.web.session;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class SessionCountProducer implements HttpSessionListener, IStatsProducer{

	/**
	 * SessionCount stats object.
	 */
	private SessionCountStats stats;
	/**
	 * List which contains exactly one stat object.
	 */
	private List<IStats> statsList;
	
	public SessionCountProducer(){
		
		stats = new SessionCountStats();
		
		statsList = new ArrayList<IStats>();
		statsList.add(stats);
		
		IProducerRegistry reg = ProducerRegistryFactory.getProducerRegistryInstance();
		reg.registerProducer(this);
	}
	
	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		stats.notifySessionCreated();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		stats.notifySessionDestroyed();
	}

	@Override
	public String getCategory() {
		return "web";
	}

	@Override
	public String getProducerId() {
		return "SessionCount";
	}

	@Override
	public List<IStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}
	
	@Override public String toString(){
		return getProducerId()+" "+getStats();
		
	}
}
