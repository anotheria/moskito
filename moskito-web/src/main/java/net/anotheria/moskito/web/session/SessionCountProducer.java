package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.AbstractBuiltInProducer;
import net.anotheria.moskito.core.util.session.SessionCountStats;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This producer attaches itself to webcontainer session management and counts created and deleted sessions.
 * @author lrosenberg
 *
 */
public class SessionCountProducer extends AbstractBuiltInProducer<SessionCountStats> implements HttpSessionListener, IStatsProducer<SessionCountStats> {

	/**
	 * SessionCount stats object.
	 */
	private SessionCountStats stats;
	/**
	 * List which contains exactly one stat object.
	 */
	private List<SessionCountStats> statsList;

	/**
	 * Creates a new SessionCountProducer.
	 */
	public SessionCountProducer(){
		
		stats = new SessionCountStats();
		
		statsList = new ArrayList<>(1);
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
	public List<SessionCountStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}
	
	@Override public String toString(){
		return getProducerId()+ ' ' +getStats();
		
	}
}
