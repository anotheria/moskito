package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.context.CurrentMeasurement;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.EntryCountLimitedOnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.predefined.FilterStatsFactory;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.web.filters.caseextractor.FilterCaseExtractor;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A generic filter that supports multiple tag extractors. This filter replaced the single-use filters like RefererFilter, RequestURIFilter etc. Main purpose was to reduce code as well as get shorter stack traces.
 * The filter is implementing IStatsProducer interface but only to be able to 'mark' itself in the path, it doesn't actually collect own stats.
 *
 * @author lrosenberg
 * @since 26.04.16 19:04
 */
public class GenericMonitoringFilter implements Filter, IStatsProducer {
	/**
	 * Logger instance.
	 */
	private Logger log = LoggerFactory.getLogger(getClass());
	/**
	 * Parameter name for the init parameter for the limit of dynamic case names (number of names) in the filter config. If the number of cases will exceed this limit,
	 * the new cases will be ignored (to prevent memory leakage).
	 */
	public static final String INIT_PARAM_LIMIT = "limit";

	/**
	 * Constant for use-cases which are over limit. In case we gather all request urls and we set a limit of 1000 it may well happen, that we actually have more than the set limit.
	 * In this case it is good to know how many requests those, 'other' urls produce.
	 */
	public static final String OTHER = "-other-";

	/**
	 * Case extractors. Each extractor can extract the monitoring cases by some parameter, for example URI, Referer, User-Agent etc.
	 */
	private final Map<FilterCaseExtractor, OnDemandStatsProducer<FilterStats>> extractorMap = new HashMap<>();

	private void beforeExecution(HttpServletRequest req, HttpServletResponse res){
		for (Map.Entry<FilterCaseExtractor, OnDemandStatsProducer<FilterStats>> extractorEntry : extractorMap.entrySet()) {
			OnDemandStatsProducer<FilterStats> producer = extractorEntry.getValue();
			FilterStats defaultStats = producer.getDefaultStats();
			String caseName = extractorEntry.getKey().extractCaseName(req);
			FilterStats caseStats = getCaseStats(caseName, producer);

			defaultStats.addRequest();
			if (caseStats != null) {
				caseStats.addRequest();
			}
		}
	}

	private FilterStats getCaseStats(String caseName, OnDemandStatsProducer<FilterStats> producer){
		if (caseName == null)
			return null;
		try {
			return producer.getStats(caseName);
		} catch (OnDemandStatsProducerException e) {
			log.debug("Couldn't get stats for case : " + caseName + ", probably limit reached");
			try {
				return producer.getStats(OTHER);
			} catch (OnDemandStatsProducerException e1) {
				log.error("This can't happen, 'OtherStats' aka " + OTHER + " doesn't exists in producer for case " + caseName);
			}
		}
		return null;
	}

	private void afterExecution(HttpServletRequest req, HttpServletResponse res, long executionTime, Throwable t){

		double executionTimeInMicroseconds = ((double)executionTime)/1000;
		//TODO removed ServerTiming header, because it is yet too later (after the execution)
		//res.setHeader("Server-Timing", "'execution="+executionTimeInMicroseconds+"; \"execution time μs\"'");
		for (Map.Entry<FilterCaseExtractor, OnDemandStatsProducer<FilterStats>> extractorEntry : extractorMap.entrySet()){
			OnDemandStatsProducer<FilterStats> producer = extractorEntry.getValue();
			FilterStats defaultStats = producer.getDefaultStats();
			String caseName = extractorEntry.getKey().extractCaseName(req);

			FilterStats caseStats = getCaseStats(caseName, producer);

			defaultStats.addExecutionTime(executionTime);
			defaultStats.notifyRequestFinished();

			if (caseStats!=null) {
				caseStats.addExecutionTime(executionTime);
				caseStats.notifyRequestFinished();
			}

			if (t!=null){
				if (t instanceof ServletException){
					defaultStats.notifyServletException(t);
					if (caseStats!=null)
						caseStats.notifyServletException(t);
				}else if(t instanceof IOException){
					defaultStats.notifyIOException(t);
					if (caseStats!=null)
						caseStats.notifyIOException(t);
				}else if(t instanceof RuntimeException){
					defaultStats.notifyRuntimeException(t);
					if (caseStats!=null)
						caseStats.notifyRuntimeException(t);
				}else{
					defaultStats.notifyError(t);
					if (caseStats!=null)
						caseStats.notifyError();
				}
			}
		}
	}

	@Override public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		if (! (req instanceof  HttpServletRequest)){
			chain.doFilter(req, res);
			return;
		}

		beforeExecution((HttpServletRequest)req, (HttpServletResponse)res);

		CurrentMeasurement measurement = MoSKitoContext.get().notifyProducerEntry(this);
		if (measurement.isFirst()){
			//build request description.
			HttpServletRequest httpServletRequest = (HttpServletRequest) req;
			StringBuilder callDescription = new StringBuilder();
			callDescription.append(httpServletRequest.getRequestURI());
			if (httpServletRequest.getQueryString()!=null && httpServletRequest.getQueryString().length()>0){
				callDescription.append('?').append(httpServletRequest.getQueryString());
			}
			measurement.setCallDescription(callDescription.toString());

		}

		long startTime = System.nanoTime();
		Throwable t = null;

		try{
			chain.doFilter(req, res);
		}catch(ServletException e){
			t = e;
		}catch(IOException e){
			t = e;
		}catch(RuntimeException e){
			t = e;
		}catch(Error e){
			t = e;
		}finally{
			long exTime = System.nanoTime() - startTime;
			afterExecution( (HttpServletRequest)req, (HttpServletResponse) res, exTime, t);
			if (measurement.isFirst()){
				measurement.notifyProducerFinished();
			}
			MoSKitoContext.get().notifyProducerExit(this);
		}
	}

	@Override public void init(FilterConfig config) throws ServletException {
		int limit = -1;
		String pLimit = config.getInitParameter(INIT_PARAM_LIMIT);
		if (pLimit!=null) {
			try {
				limit = Integer.parseInt(pLimit);
			} catch (NumberFormatException ignored) {
				log.warn("couldn't parse limit \"" + pLimit + "\", assume -1 aka no limit.");
			}
		}
		MoskitoConfiguration configuration = MoskitoConfigurationHolder.getConfiguration();
		net.anotheria.moskito.core.config.filter.FilterConfig filterConfig = configuration.getFilterConfig();
		String[] extractorNames = filterConfig.getCaseExtractors();
		if (extractorNames != null && extractorNames.length>0){
			for (String extractorName : extractorNames){
				try {
					FilterCaseExtractor extractor = (FilterCaseExtractor)Class.forName(extractorName).newInstance();
					OnDemandStatsProducer<FilterStats> onDemandProducer = limit == -1 ?
							new OnDemandStatsProducer<FilterStats>(extractor.getProducerId(), extractor.getCategory(), extractor.getSubsystem(), new FilterStatsFactory(getMonitoringIntervals())) :
							new EntryCountLimitedOnDemandStatsProducer<FilterStats>(extractor.getProducerId(), extractor.getCategory(), extractor.getSubsystem(), new FilterStatsFactory(getMonitoringIntervals()), limit);
					ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
					extractorMap.put(extractor, onDemandProducer);
					//force request uri filter to create 'other' stats.
					try{
						//force creation of 'other' stats.
						if (limit!=-1)
							onDemandProducer.getStats(OTHER);
					}catch(OnDemandStatsProducerException e){
						log.error("Can't create 'other' stats for limit excess", e);
					}
				} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
					log.error("Can't load filter case extractor "+extractorName, e);
				}


			}
		}


	}

	@Override public void destroy(){

	}

	protected Interval[] getMonitoringIntervals(){
		return Constants.getDefaultIntervals();
	}

	@Override
	public List getStats() {
		return Collections.emptyList();
	}

	@Override
	public String getProducerId() {
		return getClass().getSimpleName();
	}

	@Override
	public String getCategory() {
		return "filter";
	}

	@Override
	public String getSubsystem() {
		return "web";
	}
}
