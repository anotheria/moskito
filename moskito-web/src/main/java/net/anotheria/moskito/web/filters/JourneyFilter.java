package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.NoTracedCall;
import net.anotheria.moskito.core.calltrace.RunningTraceContainer;
import net.anotheria.moskito.core.calltrace.TracedCall;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tagging.CustomTag;
import net.anotheria.moskito.core.config.tagging.CustomTagSource;
import net.anotheria.moskito.core.config.tagging.TaggingConfig;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import net.anotheria.moskito.core.tag.TagType;
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
public class JourneyFilter implements Filter {

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
	private static final String PARAM_JOURNEY_NAME = "mskJourneyName";

	private static final String TAG_IP = "ip";
	private static final String TAG_REFERER = "referer";
	private static final String TAG_USER_AGENT = "user-agent";
	private static final String TAG_SESSION_ID = "sessionId";
	private static final String TAG_URL = "url";
	private static final String TAG_SERVER_NAME = "serverName";

	/**
	 * Log.
	 */
	private static final Logger log = LoggerFactory.getLogger(JourneyFilter.class);

	/**
	 * JourneyManager instance.
	 */
	private JourneyManager journeyManager;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain) throws IOException, ServletException {
		if (!(sreq instanceof HttpServletRequest)) {
			chain.doFilter(sreq, sres);
			return;
		}

		HttpServletRequest req = (HttpServletRequest) sreq;
		HttpSession session = req.getSession(false);
		processParameters(req);

		//autoset tags.
		TaggingConfig taggingConfig = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig();
		if (taggingConfig.isAutotagIp()) {
			MoSKitoContext.addTag(TAG_IP, req.getRemoteAddr(), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_IP);
		}
		if (taggingConfig.isAutotagReferer()) {
			MoSKitoContext.addTag(TAG_REFERER, req.getHeader(TAG_REFERER), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_REFERER);
		}
		if (taggingConfig.isAutotagUserAgent()) {
			MoSKitoContext.addTag(TAG_USER_AGENT, req.getHeader(TAG_USER_AGENT), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_USER_AGENT);
		}
		if (taggingConfig.isAutotagSessionId() && session != null) {
			MoSKitoContext.addTag(TAG_SESSION_ID, session.getId(), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_SESSION_ID);
		}
		if (taggingConfig.isAutotagUrl()) {
		    MoSKitoContext.addTag(TAG_URL, req.getQueryString() == null || req.getQueryString().isEmpty() ? req.getRequestURL().toString() : req.getRequestURL().append('?').append(req.getQueryString()).toString(), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_URL);
        }
        if (taggingConfig.isAutotagServerName()) {
		    MoSKitoContext.addTag(TAG_SERVER_NAME, req.getServerName(), TagType.BUILTIN, TagType.BUILTIN.getName() + '.' + TAG_SERVER_NAME);
        }

		//set custom tags
		for (CustomTag tag : taggingConfig.getCustomTags()) {
			if (CustomTagSource.HEADER.getName().equals(tag.getAttributeSource())) {
				MoSKitoContext.addTag(tag.getName(), req.getHeader(tag.getAttributeName()), TagType.CONFIGURED, tag.getAttribute());
			} else if (CustomTagSource.REQUEST.getName().equals(tag.getAttributeSource())) {
				MoSKitoContext.addTag(tag.getName(), (String) req.getAttribute(tag.getAttributeName()), TagType.CONFIGURED, tag.getAttribute());
			} else if (CustomTagSource.SESSION.getName().equals(tag.getAttributeSource()) && session != null) {
				MoSKitoContext.addTag(tag.getName(), (String) session.getAttribute(tag.getAttributeName()), TagType.CONFIGURED, tag.getAttribute());
			} else if (CustomTagSource.PARAMETER.getName().equals(tag.getAttributeSource())) {
				MoSKitoContext.addTag(tag.getName(), req.getParameter(tag.getAttributeName()), TagType.CONFIGURED, tag.getAttribute());
			}
		}
		//end of tags.

		JourneyRecord record = null;
		Journey journey = null;

		if (session != null) {
			record = (JourneyRecord) session.getAttribute(SA_JOURNEY_RECORD);
			if (record != null) {
				try {
					journey = journeyManager.getJourney(record.getName());
				} catch (NoSuchJourneyException e) {
					journey = journeyManager.createJourney(record.getName());
				}
			}
		}

		String url = "none";
		if (record != null) {
			url = req.getServletPath();
			if (req.getPathInfo() != null)
				url += req.getPathInfo();
			if (req.getQueryString() != null)
				url += '?' + req.getQueryString();
			RunningTraceContainer.startTracedCall(record.getUseCaseName() + '-' + url);
		}
		try {
			//Removed reset call, cause the context gets reset at the end of the call in finally anyway, so its safe to assume that we have a new context.
			//MoSKitoContext.get().reset();
			chain.doFilter(sreq, sres);
		} finally {
			if (record != null) {
				TracedCall last = RunningTraceContainer.endTrace();
				if (last instanceof NoTracedCall) {
					log.warn("Unexpectedly last is a NoTracedCall instead of CurrentlyTracedCall for " + url);
				} else {
					CurrentlyTracedCall finishedCall = (CurrentlyTracedCall) last;
					finishedCall.setEnded();
					journey.addUseCase(finishedCall);
				}

				//removes the running use case to cleanup the thread local. Otherwise tomcat will be complaining...
				RunningTraceContainer.cleanup();
			}
			MoSKitoContext.cleanup();
		}

	}


	private void processParameters(HttpServletRequest req) {


		String command = req.getParameter(PARAM_JOURNEY_RECORDING);
		String name = req.getParameter(PARAM_JOURNEY_NAME);

		if (command == null)
			return;
		if (command.equals(PARAM_VALUE_STOP)) {
			HttpSession session = req.getSession(false);
			if (session != null) {
				session.removeAttribute(SA_JOURNEY_RECORD);
			}
			try {
				journeyManager.getJourney(name).setActive(false);
			} catch (NoSuchJourneyException ignored) {
			}
		}

		if (command.equals(PARAM_VALUE_START)) {
			HttpSession session = req.getSession();
			if (name == null || name.length() == 0)
				name = "unnamed" + System.currentTimeMillis();
			session.setAttribute(SA_JOURNEY_RECORD, new JourneyRecord(name));
			journeyManager.createJourney(name);
		}
	}


	@Override
	public void init(FilterConfig chain) {
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

}

