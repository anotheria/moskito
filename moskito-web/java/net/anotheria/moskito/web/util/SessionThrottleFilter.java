package net.anotheria.moskito.web.util;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.NoSuchProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.web.session.SessionCountStats;
import org.apache.log4j.Logger;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.ConfigureMe;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This class throttles new sessions by max amount of sessions.
 *
 * @author lrosenberg
 * @since 20.05.13 23:27
 */
public class SessionThrottleFilter implements Filter{

	public static final String SESSION_SEEN_FLAG = SessionThrottleFilter.class.getName()+"Flag";

	private SessionThrottleFilterConfig throttleConfig;

	private IStatsProducer<SessionCountStats> sessionProducer;

	private static Logger log = Logger.getLogger(SessionThrottleFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		String configSource = filterConfig.getInitParameter("config");
		if (configSource==null || configSource.length()==0)
			configSource = "web.xml";

		if (configSource.equalsIgnoreCase("web.xml")){
			throttleConfig = getConfigFromWebXML(filterConfig);
		}

		if (configSource.equalsIgnoreCase("configureme")){
			throttleConfig = getConfigFromConfigureMe();
		}


		try{
			sessionProducer = (IStatsProducer<SessionCountStats>) new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer("SessionCount");
		}catch(NoSuchProducerException e){
			log.error("can't connect to SessionCountProducer, ensure that "+net.anotheria.moskito.web.session.SessionCountProducer.class.getName()+" is declared as listener");
			throttleConfig = null;
		}

		System.out.println("\n\n%%% THROTTLE CONFIG "+throttleConfig+"\n\n");
	}

	private SessionThrottleFilterConfig getConfigFromConfigureMe(){
		SessionThrottleFilterConfig config = new SessionThrottleFilterConfig();
		try{
			ConfigurationManager.INSTANCE.configure(config);
		}catch(IllegalArgumentException e){
			log.warn("Incompatible configuration selected, configureme as source choosen, but sessionthrottle.json is not present");
		}
		return config;
	}

	private SessionThrottleFilterConfig getConfigFromWebXML(FilterConfig filterConfig){
		int limit = -1;

		String l = filterConfig.getInitParameter("limit");
		try{
			if (l!=null)
				limit = Integer.parseInt(l);
		}catch(NumberFormatException ignored){
			//ignored.
		}

		String target = filterConfig.getInitParameter("redirectTarget");
		if (target==null || target.length()==0)
			limit = -1;

		return new SessionThrottleFilterConfig(limit, target);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (throttleConfig==null || throttleConfig.getLimit()==-1 || (!(request instanceof HttpServletRequest))){
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null || session.getAttribute(SESSION_SEEN_FLAG)==null){
			if (sessionProducer.getStats().get(0).getCurrentSessionCount(null)>throttleConfig.getLimit()){
				//have to cancel request.
				((HttpServletResponse)response).sendRedirect(throttleConfig.getTarget());
				return;
			}
			if (session!=null)
				session.setAttribute(SESSION_SEEN_FLAG, Boolean.TRUE);
		}

		chain.doFilter(request, response);


	}

	@Override
	public void destroy() {

	}

	@ConfigureMe(name="sessionthrottle", allfields=true)
	public static class SessionThrottleFilterConfig{
		private int limit = -1;
		private String target;

		public SessionThrottleFilterConfig(){

		}

		public SessionThrottleFilterConfig(int aLimit, String aTarget){
			limit = aLimit;
			target = aTarget;
		}

		public int getLimit() {
			return limit;
		}

		public void setLimit(int limit) {
			this.limit = limit;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		@Override public String toString(){
			return "limit: "+getLimit()+", Target: "+getTarget();
		}

		@AfterConfiguration public void logInfo(){
			log.info("Configured session throttle "+this);
		}
	}
}
