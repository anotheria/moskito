package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.dynamic.EntryCountLimitedOnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.JSStats;
import net.anotheria.moskito.core.predefined.JSStatsFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	 * {@link Logger} instance.
	 */
	private Logger log;
	/**
	 * {@link OnDemandStatsProducer} instance.
	 */
	private OnDemandStatsProducer<JSStats> onDemandProducer;

	/**
	 * Constructor.
	 */
	public JSTalkBackFilter() {
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		int limit = -1;
		String pLimit = filterConfig.getInitParameter(INIT_PARAM_LIMIT);
		if (pLimit != null) {
			try {
				limit = Integer.parseInt(pLimit);
			} catch (NumberFormatException ignored) {
				log.warn("couldn't parse limit \"" + pLimit + "\", assume -1 aka no limit.");
			}
		}

		onDemandProducer = limit == -1 ? new OnDemandStatsProducer<JSStats>(getProducerId(), getCategory(), getSubSystem(), new JSStatsFactory()) :
				new EntryCountLimitedOnDemandStatsProducer<JSStats>(getProducerId(), getCategory(), getSubSystem(), new JSStatsFactory(), limit);

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
		if (onDemandProducer == null) {
			log.error("Access to filter before it's inited!");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			final String urlPath = request.getParameter(URL);
			if (StringUtils.isEmpty(urlPath)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}

			final String domLoadTime = request.getParameter(DOM_LOAD_TIME);
			final String windowLoadTime = request.getParameter(WINDOW_LOAD_TIME);

			try {
				final JSStats jsStats = onDemandProducer.getStats(urlPath);
				if (!StringUtils.isEmpty(domLoadTime) && isLong(domLoadTime) && Long.valueOf(domLoadTime) > 0)
					jsStats.addDOMLoadTime(Long.valueOf(domLoadTime));
				if (!StringUtils.isEmpty(windowLoadTime) && isLong(windowLoadTime) && Long.valueOf(windowLoadTime) > 0)
					jsStats.addWindowLoadTime(Long.valueOf(windowLoadTime));

				writeNoContentResponse(response);
			} catch (OnDemandStatsProducerException e) {
				log.info("Couldn't get stats for : " + urlPath + ", probably limit reached");
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
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
	public String getProducerId() {
		return getClass().getSimpleName();
	}

	/**
	 * Returns producer category name.
	 *
	 * @return producer category name
	 */
	public String getCategory() {
		return "filter";
	}

	/**
	 * Returns subsystem name.
	 *
	 * @return subsystem name
	 */
	public String getSubSystem() {
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
