package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.AbstractBuiltInProducer;
import net.anotheria.moskito.core.util.session.SessionCountFactory;
import net.anotheria.moskito.core.util.session.SessionCountStats;
import net.anotheria.moskito.web.filters.SourceTldFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.net.InetAddress;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.13 23:40
 */
public class SessionByTldListener implements HttpSessionListener, ServletRequestListener {

	/**
	 * The internal producer instance.
	 */
	private static OnDemandStatsProducer<SessionCountStats> onDemandProducer;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(SessionByTldListener.class);

	public static final String ATT_NAME = "_MoSKito_SessionByTldFilter_TLD";

	static{
		onDemandProducer = new OnDemandStatsProducer<SessionCountStats>("SessionCountByTld", "web", AbstractBuiltInProducer.SUBSYSTEM_BUILTIN, SessionCountFactory.DEFAULT_INSTANCE);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
	}


	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//do nothing.
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		countDestroyedSession((String)se.getSession().getAttribute(ATT_NAME));
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		//do nothing.
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		if (!(sre.getServletRequest() instanceof  HttpServletRequest))
			return;
		HttpServletRequest req = (HttpServletRequest)sre.getServletRequest();
		if (req.getSession(false)==null){
			return;
		}

		//Only put attribute once.
		HttpSession session = req.getSession();
		if (session.getAttribute(ATT_NAME)!=null){
			return;
		}

		String ip = req.getRemoteAddr();
		//recheck if the attribute is still not there, and if so, put a temporarly attribute to prevent duplicate session counting.
		synchronized (session){
			if (session.getAttribute(ATT_NAME)!=null){
				return;
			}
			session.setAttribute(ATT_NAME, ip);
		}

		String hostName;

		try{
			hostName = InetAddress.getByName(ip).getHostName();
			int indexOfTld = hostName.lastIndexOf('.');
			if (indexOfTld!=-1){
				hostName = hostName.substring(indexOfTld+1);
			}
			if (hostName.length()> SourceTldFilter.TLD_LENGTH_LIMIT){
				hostName = hostName.substring(0, SourceTldFilter.TLD_LENGTH_LIMIT);
			}

			if (Character.isDigit(hostName.charAt(hostName.length()-1))){
				hostName = "-unresolved-";
			}
		}catch(Exception e){
			hostName = "ERROR";
		}
		session.setAttribute(ATT_NAME, hostName);
		countCreatedSession(hostName);

	}


	private static void countCreatedSession(String tld){
		onDemandProducer.getDefaultStats().notifySessionCreated();
		try{
			onDemandProducer.getStats(tld).notifySessionCreated();
		}catch(OnDemandStatsProducerException e){
			log.warn("sessionCreated("+tld+"), e");
		}
	}

	private static void countDestroyedSession(String tld){
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
