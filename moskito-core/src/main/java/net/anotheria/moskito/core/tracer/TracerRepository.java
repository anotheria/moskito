package net.anotheria.moskito.core.tracer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:31
 */
public class TracerRepository {
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(TracerRepository.class);

	/**
	 * Singleton instance of this class.
	 */
	private static TracerRepository INSTANCE = new TracerRepository();

	private ConcurrentMap<String,Tracer> tracers = new ConcurrentHashMap<String, Tracer>();


	/**
	 * Private constructor.
	 */
	private TracerRepository(){

	}

	/**
	 * Returns the singleton instance of the TracerRepository.
	 * @return
	 */
	public static TracerRepository getInstance(){ return INSTANCE; }

	public boolean isTracingEnabledForProducer(String producerId){
		Tracer tracer = tracers.get(producerId);
		return tracer != null && tracer.isEnabled();

	}

	public void addTracedExecution(String call, StackTraceElement[] stackTraceElements, long executionTime){
		System.out.println("Add traced execution "+call+", "+stackTraceElements+", "+executionTime);
	}

	public void enableTracingForProducerId(String producerId){
		Tracer newTracer = new Tracer(producerId);
		Tracer old = tracers.putIfAbsent(producerId, newTracer);
		if (old!=null)
			old.setEnabled(true);
	}

	public void disableTrackingForProducerId(String producerId){
		Tracer tracer = tracers.get(producerId);
		if (tracer != null)
			tracer.setEnabled(false);
	}

	public void removeTracer(String producerId){
		tracers.remove(producerId);
	}

	public List<Tracer> getTracers(){
		return new ArrayList<Tracer>(tracers.values());
	}

}
