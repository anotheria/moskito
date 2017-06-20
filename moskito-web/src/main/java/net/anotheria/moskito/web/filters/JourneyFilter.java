package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.NoTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * This filter checks if the journey tracing is active for current http session and if so switches the call tracing on.
 */
public class JourneyFilter implements Filter{
	
	/**
	 * Session attribute name for session record.
	 */
	/*package visible*/static final String SA_JOURNEY_RECORD = "mskJourneyRecord";
	
	private static final String PARAM_JOURNEY_RECORDING = "mskJourney";
	/**
	 * The value of the parameter for the session monitoring start.
	 */
	private static final String PARAM_VALUE_START = "start";
	/**
	 * The value of the parameter for the session monitoring stop.
	 */
	private static final String PARAM_VALUE_STOP = "stop";

	/**
	 * Parameter name for the name of the journey.
	 */
	public static final String PARAM_JOURNEY_NAME = "mskJourneyName";

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(JourneyFilter.class);

	/**
	 * JourneyManager instance.
	 */
	private JourneyManager journeyManager;

	@Override public void destroy() {
	}

	@Override public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)){
			chain.doFilter(sreq, sres);
			return;
		}

		boolean always_record_journey  = true;

		HttpServletRequest req = (HttpServletRequest)sreq;
		processParameters(req);

		HttpSession session = req.getSession(false);
		JourneyRecord record = null;
		Journey journey = null;

		if (session!=null){
			record = (JourneyRecord)session.getAttribute(SA_JOURNEY_RECORD);
			if (record!=null){
				try{
					journey = journeyManager.getJourney(record.getName());
				}catch(NoSuchJourneyException e){
					journey = journeyManager.createJourney(record.getName());
				}
			}
		}

		String url = "none";
		if (record!=null || always_record_journey){
			url = req.getServletPath();
			if (req.getPathInfo()!=null)
				url += req.getPathInfo();
			if (req.getQueryString()!=null)
				url += '?' +req.getQueryString();
			RunningTraceContainer.startTracedCall(record == null ? "RND": record.getUseCaseName()+ '-' +url);
		}
		try{
			MoSKitoContext.get().reset();
			chain.doFilter(sreq, sres);
			System.out.println("FINISHED JOURNEY");
		}finally{
			MoSKitoContext.cleanup();

			if (record!=null ||always_record_journey){
				TracedCall last = RunningTraceContainer.endTrace();
				if (last instanceof NoTracedCall){
					log.warn("Unexpectedly last is a NoTracedCall instead of CurrentlyTracedCall for "+url);
				}else {
					CurrentlyTracedCall finishedCall = (CurrentlyTracedCall)last;
					finishedCall.setEnded();
					if (record!=null)
						journey.addUseCase(finishedCall);
					serializeOut(finishedCall);
				}
				
				//removes the running use case to cleanup the thread local. Otherwise tomcat will be complaining...
				RunningTraceContainer.cleanup();
			}
		}
			
	}

	private void serializeOut(CurrentlyTracedCall call){
		System.out.println("Hace to serialize call "+call);
		System.out.println("Duration "+call.getDurationNanos()+" ns ");
		TraceStep root = call.getRootStep();
		System.out.println(root.toJSON());
	}
	
	private void processParameters(HttpServletRequest req){
		
		
		String command = req.getParameter(PARAM_JOURNEY_RECORDING);
		String name = req.getParameter(PARAM_JOURNEY_NAME);
		
		if (command==null)
			return;
		if (command.equals(PARAM_VALUE_STOP)){
			HttpSession session = req.getSession(false);
			if (session!=null){
				session.removeAttribute(SA_JOURNEY_RECORD);
			}
			try{
				journeyManager.getJourney(name).setActive(false);
			}catch(NoSuchJourneyException ignored){}
		}
		
		if (command.equals(PARAM_VALUE_START)){
			HttpSession session = req.getSession();
			if (name==null || name.length()==0)
				name = "unnamed"+System.currentTimeMillis();
			session.setAttribute(SA_JOURNEY_RECORD, new JourneyRecord(name));
			journeyManager.createJourney(name);
		}
	}
	

	@Override public void init(FilterConfig chain) throws ServletException {
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}
	
}

