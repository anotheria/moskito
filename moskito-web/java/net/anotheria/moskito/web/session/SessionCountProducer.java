package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This producer attaches itself to webcontainer session management and 
 * @author another
 *
 */
public class SessionCountProducer implements HttpSessionListener, IStatsProducer {

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
	public void sessionCreated(HttpSessionEvent event) {
		stats.notifySessionCreated();
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
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
