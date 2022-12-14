package net.anotheria.moskito.core.tracer;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tracing.TracingConfiguration;
import net.anotheria.util.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Repository where tracers are saved.
 *
 * @author lrosenberg
 * @since 05.05.15 00:31
 */
public class TracerRepository {

	private static final String ALL_STATS_STATNAME = "*";

	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(TracerRepository.class);

	/**
	 * Log for logged traces.
	 */
	private static final Logger traceLog = LoggerFactory.getLogger("MoSKitoTracer");

	/**
	 * Singleton instance of this class.
	 */
	private static final TracerRepository INSTANCE = new TracerRepository();

	/**
	 * Currently existing tracers.
	 */
	private final ConcurrentMap<String,Tracer> tracers = new ConcurrentHashMap<>();

	/**
	 * Private constructor.
	 */
	private TracerRepository(){
	}

	/**
	 * Returns the singleton instance of the TracerRepository.
	 * @return
	 */
	public static TracerRepository getInstance(){
		return INSTANCE;
	}

	/**
	 * Returns true if tracing is enabled globally and in particular for this producer.
	 * @param producerId
	 * @return
	 */
	public boolean isTracingEnabledForProducer(String producerId, String statName){
		//check if tracing is completely disabled.
		if (!MoskitoConfigurationHolder.getConfiguration().getTracingConfig().isTracingEnabled())
			return false;

		Tracer tracer = tracers.get(makeKey(producerId, statName));
		if (tracer != null) {
			return tracer.isEnabled();
		}

		tracer = tracers.get(makeKey(producerId, ALL_STATS_STATNAME));
		return tracer != null && tracer.isEnabled();

	}

	public String getEffectiveTracerId(String producerId, String statName){
		Tracer myTracer = getTracer(makeKey(producerId, statName));
		if (myTracer!=null)
			return myTracer.getTracerId();
		Tracer allStatsTracer = getTracer(makeKey(producerId, ALL_STATS_STATNAME));
		if (allStatsTracer!=null)
			return allStatsTracer.getTracerId();
		throw new IllegalArgumentException("No tracer found for producer "+producerId+" and stat "+statName);
	}

	public void addTracedExecution(String producerId, String statName, Trace aNewTrace){

		TracingConfiguration config = MoskitoConfigurationHolder.getConfiguration().getTracingConfig();
		if (!config.isTracingEnabled())
			return ;

		if (config.isInspectEnabled()) {
			Tracer myTracer = getTracer(makeKey(producerId, statName));
			if (myTracer == null) {
				myTracer = getTracer(makeKey(producerId, ALL_STATS_STATNAME));
				if (myTracer==null) {
					log.warn("Got a new incoming trace, but not tracer! ProducerId: " + producerId + ", Call: " + aNewTrace.getCall());
					return;
				}
			}

			myTracer.addTrace(aNewTrace, config.getToleratedTracesAmount(), config.getMaxTraces());
		}

		if (config.isLoggingEnabled()){
			traceLog.info(NumberUtils.makeISO8601TimestampString()+", call: "+aNewTrace.getCall()+" duration: "+aNewTrace.getDuration());
			for (StackTraceElement e : aNewTrace.getElements()){
				traceLog.info('\t' +e.toString());
			}
		}
	}

	public void enableTracingForProducerId(String producerId, String statName){
		String tracerId = makeKey(producerId, statName);
		enableTracer(tracerId);
	}

	public void enableTracer(String tracerId){
		String producerId = getProducerIdFromTracerId(tracerId);
		String statName = getStatNameFromTracerId(tracerId);
		Tracer newTracer = new Tracer(producerId, statName);
		Tracer old = tracers.putIfAbsent(tracerId, newTracer);
		if (old!=null)
			old.setEnabled(true);
	}

	private String getStatNameFromTracerId(String tracerId) {
		return tracerId.substring(tracerId.indexOf('.')+1);
	}

	private String getProducerIdFromTracerId(String tracerId) {
		return tracerId.substring(0, tracerId.indexOf('.'));
	}

	public void disableTracer(String tracerId){
		Tracer tracer = tracers.get(tracerId);
		if (tracer != null)
			tracer.setEnabled(false);
	}

	static String makeKey(String producerId, String statName) {
		if (statName == null || statName.length()==0)
			statName = ALL_STATS_STATNAME;
		return producerId+"."+statName;
	}


	public void removeTracer(String tracerId){
		tracers.remove(tracerId);
	}

	public List<Tracer> getTracers(){
		return new ArrayList<Tracer>(tracers.values());
	}

	public Tracer getTracer(String tracerId) {
		return tracers.get(tracerId);
	}

	public List<Trace> getTraces(String tracerId){
		try {
			return getTracer(tracerId).getTraces();
		}catch(NullPointerException e){
			throw new IllegalArgumentException("No traces for tracerId: "+tracerId);
		}
	}

}
