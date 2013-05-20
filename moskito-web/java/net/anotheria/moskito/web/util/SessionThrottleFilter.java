package net.anotheria.moskito.web.util;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.NoSuchProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.web.session.SessionCountStats;
import org.apache.log4j.Logger;

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

	private String target;

	private int limit = -1;

	private IStatsProducer<SessionCountStats> sessionProducer;

	private static Logger log = Logger.getLogger(SessionThrottleFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String l = filterConfig.getInitParameter("limit");
		try{
			if (l!=null)
			limit = Integer.parseInt(l);
		}catch(NumberFormatException ignored){
			//ignored.
		}

		target = filterConfig.getInitParameter("redirectTarget");
		if (target==null || target.length()==0)
			limit = -1;

		try{
			sessionProducer = (IStatsProducer<SessionCountStats>) new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer("SessionCount");
		}catch(NoSuchProducerException e){
			log.error("can't connect to SessionCountProducer, ensure that "+net.anotheria.moskito.web.session.SessionCountProducer.class.getName()+" is declared as listener");
			limit = -1;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (limit==-1 || (!(request instanceof HttpServletRequest))){
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null || session.getAttribute(SESSION_SEEN_FLAG)==null){
			if (sessionProducer.getStats().get(0).getCurrentSessionCount(null)>limit){
				//have to cancel request.
				((HttpServletResponse)response).sendRedirect(target);
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
}
