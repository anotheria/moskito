package net.anotheria.moskito.web.filters.sessionbytld;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.web.session.SessionCountFactory;
import net.anotheria.moskito.web.session.SessionCountStats;
import org.apache.log4j.Logger;

/**
 * This class encapsulates producer logic for both filter and listener.
 *
 * @author lrosenberg
 * @since 26.04.13 23:41
 */
public enum SessionByTldProducer {
	INSTANCE;

	private static Logger log = Logger.getLogger(SessionByTldProducer.class);

	/**
	 * The internal producer instance.
	 */
	private OnDemandStatsProducer<SessionCountStats> onDemandProducer;

	private SessionByTldProducer(){
		onDemandProducer = new OnDemandStatsProducer<SessionCountStats>("SessionCountByTld", "web", IStatsProducer.SUBSYSTEM_BUILTIN, SessionCountFactory.DEFAULT_INSTANCE);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
	}

	public void sessionCreated(String tld){
		onDemandProducer.getDefaultStats().notifySessionCreated();
		try{
			onDemandProducer.getStats(tld).notifySessionCreated();
		}catch(OnDemandStatsProducerException e){
			log.warn("sessionCreated("+tld+"), e");
		}
	}

	public void sessionDestroyed(String tld){
		if (tld==null)
			return;
		onDemandProducer.getDefaultStats().notifySessionDestroyed();
		try{
			onDemandProducer.getStats(tld).notifySessionDestroyed();
		}catch(OnDemandStatsProducerException e){
			log.warn("sessionCreated("+tld+"), e");
		}
	}
}
