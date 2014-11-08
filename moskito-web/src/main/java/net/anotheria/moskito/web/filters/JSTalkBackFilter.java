package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.JSStats;
import net.anotheria.moskito.core.predefined.JSStatsFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.09.13 22:44
 */
public class JSTalkBackFilter implements Filter {
	/**
	 * Logger instance, available for all subclasses.
	 */
	protected Logger log;
	private OnDemandStatsProducer<JSStats> onDemandProducer;

	protected JSTalkBackFilter() {
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		onDemandProducer = new OnDemandStatsProducer<JSStats>(getProducerId(), "filter", "default", new JSStatsFactory(getMonitoringIntervals()));
//		onDemandProducer = new EntryCountLimitedOnDemandStatsProducer<JSStats>(getProducerId(), "filter", "default", new JSStatsFactory(getMonitoringIntervals()), 100);

		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		if (onDemandProducer == null) {
			log.error("Access to filter before it's inited!");
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}

		String urlPath = servletRequest.getParameter("url");
		String domLoadTime = servletRequest.getParameter("domLoadTime");
		String windowLoadTime = servletRequest.getParameter("windowLoadTime");

		try {
			JSStats jsStats = onDemandProducer.getStats(urlPath);
			if (!StringUtils.isEmpty(domLoadTime))
				jsStats.setDomLoadTime(Long.valueOf(domLoadTime));
			if (!StringUtils.isEmpty(windowLoadTime))
				jsStats.setWindowLoadTime(Long.valueOf(windowLoadTime));
		} catch (OnDemandStatsProducerException e) {
			log.info("Couldn't get stats for : " + urlPath + ", probably limit reached");

		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}

	public String getProducerId() {
		return getClass().getSimpleName();
	}

	protected Interval[] getMonitoringIntervals() {
		return Constants.getDefaultIntervals();
	}

}
