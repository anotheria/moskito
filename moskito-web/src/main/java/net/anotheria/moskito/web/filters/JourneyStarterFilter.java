package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This Filter starts a journey triggered by some external parameters, for example http headers.
 * It doesn't create a session, but if a session is there, it will be assigned to the journey.
 * TODO we have to check if the both filter coexist or fight each other.
 *
 * @author lrosenberg
 * @since 15.10.14 22:41
 */
public class JourneyStarterFilter implements Filter{

	/**
	 * Name of the header to react to.
	 */
	public static final String HEADER_NAME = "JourneyName";

	/**
	 * JourneyManager instance.
	 */
	private JourneyManager journeyManager;

	@Override public void init(FilterConfig chain) throws ServletException {
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (! (servletRequest instanceof HttpServletRequest)) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		HttpServletRequest req = (HttpServletRequest)servletRequest;
		String journeyName = null;
		String journeyNameFromHeader = req.getHeader(HEADER_NAME);
		if (journeyNameFromHeader != null)
			journeyName = journeyNameFromHeader;

		//add further possibilities to supply a journey name.


		if (journeyName == null) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}


		Journey journey = null;
		JourneyRecord record = new JourneyRecord(journeyName);
		try{
			journeyManager.getJourney(journeyName);
		}catch(NoSuchJourneyException e){
//ok, journey doesn't exist, we have to create it.
			journey = journeyManager.createJourney(journeyName);
			//start journey
			HttpSession session = req.getSession(false);

			if (session!=null) {
				session.setAttribute(JourneyFilter.SA_JOURNEY_RECORD, record);
			}

		}

		record = new JourneyRecord(journeyName);
		String url = req.getServletPath();
		if (req.getPathInfo()!=null)
			url += req.getPathInfo();
		if (req.getQueryString()!=null)
			url += '?' +req.getQueryString();
		RunningTraceContainer.startTracedCall(record.getUseCaseName() + '-' + url);

		try{
			filterChain.doFilter(servletRequest, servletResponse);
		}finally{
			TracedCall last = RunningTraceContainer.endTrace();
			journey.addCall((CurrentlyTracedCall)last);

			//removes the running use case to cleanup the thread local. Otherwise tomcat will be complaining...
			RunningTraceContainer.cleanup();
		}

	}

	@Override
	public void destroy() {

	}
}
