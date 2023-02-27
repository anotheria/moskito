package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.dynamic.EntryCountLimitedOnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.PageInBrowserStats;
import net.anotheria.moskito.core.predefined.PageInBrowserStatsFactory;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The filter collects HTML DOM related information - DOM load time, web page load time, etc.
 *
 * @author lrosenberg
 * @since 15.09.13 22:44
 */
public class JSTalkBackFilter implements Filter {
	/**
	 * Parameter name for the init parameter for the limit of dynamic case names (number of names) in the filter config. If the number of cases will exceed this limit,
	 * the new cases will be ignored (to prevent memory leakage).
	 */
	public static final String INIT_PARAM_LIMIT = "limit";
	/**
	 * Web page url path request parameter name.
	 */
	private static final String URL = "url";
	/**
	 * DOM load time request parameter name.
	 * Time value should be in milliseconds.
	 */
	private static final String DOM_LOAD_TIME = "domLoadTime";
	/**
	 * Web page load time request parameter name.
	 * Time value should be in milliseconds.
	 */
	private static final String WINDOW_LOAD_TIME = "windowLoadTime";
	/**
	 * Producer id request parameter name.
	 */
	private static final String PRODUCER_ID = "producerId";
	/**
	 * Producer category request parameter name.
	 */
	private static final String CATEGORY = "category";
	/**
	 * Producer subsystem request parameter name.
	 */
	private static final String SUBSYSTEM = "subsystem";
	/**
	 * {@link Logger} instance.
	 */
	private Logger log;
	/**
	 * Init parameter 'limit' value.
	 * See {@link #INIT_PARAM_LIMIT}.
	 */
	private int limit = -1;

	/**
	 * Constructor.
	 */
	public JSTalkBackFilter() {
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String pLimit = filterConfig.getInitParameter(INIT_PARAM_LIMIT);
		if (pLimit != null) {
			try {
				limit = Integer.parseInt(pLimit);
			} catch (NumberFormatException ignored) {
				log.warn("couldn't parse limit \"" + pLimit + "\", assume -1 aka no limit.");
			}
		}
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse))
			return;

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		final String producerId = getValueOrDefault(request, PRODUCER_ID, getDefaultProducerId());
		final String category = getValueOrDefault(request, CATEGORY, getDefaultCategory());
		final String subsystem = getValueOrDefault(request, SUBSYSTEM, getDefaultSubsystem());

		final OnDemandStatsProducer producer = getProducer(producerId, category, subsystem);
		if (producer == null)
			return;

		final String urlPath = request.getParameter(URL);
		if (StringUtils.isEmpty(urlPath))
			return;

		final String domLoadTime = request.getParameter(DOM_LOAD_TIME);
		final String windowLoadTime = request.getParameter(WINDOW_LOAD_TIME);

		try {
			final PageInBrowserStats stats = (PageInBrowserStats) producer.getStats(urlPath);
			if (isLoadTimeValid(domLoadTime) && isLoadTimeValid(windowLoadTime)){
				stats.addLoadTime(Long.parseLong(domLoadTime), Long.parseLong(windowLoadTime));
				//Also add to cumulated
				PageInBrowserStats cumulated = (PageInBrowserStats)producer.getDefaultStats();
				cumulated.addLoadTime(Long.parseLong(domLoadTime), Long.parseLong(windowLoadTime));
				
			}

			writeNoContentResponse(response);
		} catch (OnDemandStatsProducerException e) {
			log.info("Couldn't get stats for : " + urlPath + ", probably limit reached");
		}
	}

	/**
	 * Returns parameter value from request if exists, otherwise - default value.
	 *
	 * @param req          {@link HttpServletRequest}
	 * @param paramName    name of the parameter
	 * @param defaultValue parameter default value
	 * @return request parameter value
	 */
	private String getValueOrDefault(HttpServletRequest req, String paramName, String defaultValue) {
		final String value = req.getParameter(paramName);
		return StringUtils.isEmpty(value) ? defaultValue : value;
	}

	/**
	 * Validate given load time request parameter.
	 *
	 * @param loadTimeParam string representation of load time
	 * @return {@code true} if given load time string is numeric value and greater than 0, otherwise - {@code false}
	 */
	private boolean isLoadTimeValid(final String loadTimeParam) {
		return !StringUtils.isEmpty(loadTimeParam) && isLong(loadTimeParam) && Long.valueOf(loadTimeParam) > 0;
	}

	/**
	 * Returns producer by given producer id.
	 * If it was not found then new producer will be created.
	 * If existing producer is not supported then producer with default producer id will be returned.
	 * If producer with default producer id is not supported then will be returned {@code null}.
	 *
	 * @param producerId id of the producer
	 * @param category   name of the category
	 * @param subsystem  name of the subsystem
	 * @return PageInBrowserStats producer
	 */
	@SuppressWarnings("unchecked")
	private OnDemandStatsProducer<PageInBrowserStats> getProducer(final String producerId, final String category, final String subsystem) {
		final IStatsProducer statsProducer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(producerId);
		// create new
		if (statsProducer == null)
			return createProducer(producerId, category, subsystem);
		// use existing
		if (statsProducer instanceof OnDemandStatsProducer && OnDemandStatsProducer.class.cast(statsProducer).getDefaultStats() instanceof PageInBrowserStats)
			return (OnDemandStatsProducer<PageInBrowserStats>) statsProducer;

		final IStatsProducer defaultStatsProducer = ProducerRegistryFactory.getProducerRegistryInstance().getProducer(getDefaultProducerId());
		// create default
		if (defaultStatsProducer == null)
			return createProducer(getDefaultProducerId(), category, subsystem);
		// use existing default
		if (statsProducer instanceof OnDemandStatsProducer && OnDemandStatsProducer.class.cast(statsProducer).getDefaultStats() instanceof PageInBrowserStats)
			return (OnDemandStatsProducer<PageInBrowserStats>) defaultStatsProducer;

		log.warn("Can't create OnDemandStatsProducer<BrowserStats> producer with passed id: [" + producerId + "] and default id: [" + getDefaultProducerId() + "].");

		return null;
	}

	/**
	 * Creates producer with given producer id, category and subsystem and register it in producer registry.
	 *
	 * @param producerId id of the producer
	 * @param category   name of the category
	 * @param subsystem  name of the subsystem
	 * @return PageInBrowserStats producer
	 */
	private OnDemandStatsProducer<PageInBrowserStats> createProducer(final String producerId, final String category, final String subsystem) {
		OnDemandStatsProducer<PageInBrowserStats> producer = limit == -1 ? new OnDemandStatsProducer<PageInBrowserStats>(producerId, category, subsystem, new PageInBrowserStatsFactory()) :
				new EntryCountLimitedOnDemandStatsProducer<PageInBrowserStats>(producerId, category, subsystem, new PageInBrowserStatsFactory(), limit);

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
		return producer;
	}

	/**
	 * Writes {@link HttpServletResponse#SC_NO_CONTENT} to response and flushes the stream.
	 * This means that only header and empty body will be returned to the caller script.
	 *
	 * @param response {@link HttpServletResponse}
	 * @throws IOException on errors
	 */
	private void writeNoContentResponse(final HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		response.setContentType("image/gif");

		final PrintWriter out = response.getWriter();
		out.flush();
	}

	@Override
	public void destroy() {
		// do nothing
	}

	/**
	 * Returns producer id.
	 *
	 * @return producer id
	 */
	public String getDefaultProducerId() {
		return getClass().getSimpleName();
	}

	/**
	 * Returns default producer category name.
	 *
	 * @return producer category name
	 */
	public String getDefaultCategory() {
		return "default";
	}

	/**
	 * Returns default subsystem name.
	 *
	 * @return subsystem name
	 */
	public String getDefaultSubsystem() {
		return "default";
	}

	/**
	 * Checks if given string is long.
	 *
	 * @param str {@link String}
	 * @return true if it is long or false otherwise
	 */
	private boolean isLong(final String str) {
		try {
			Long.valueOf(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
