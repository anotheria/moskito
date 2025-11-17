package net.anotheria.moskito.core.tracer;

/**
 * Utility class providing naming conventions and helper methods for tracer-journey integration.
 *
 * <p>This class defines standard naming patterns used to link {@link Tracer} objects with
 * Journey tracking. Tracers are automatically integrated into the journey system, allowing
 * traces to be viewed as part of journey analysis.
 *
 * <p><b>Journey Naming:</b> Each tracer gets an associated journey with a standardized name
 * format "Traced-{tracerId}". Individual trace calls within the journey are named "Trace-{traceId}".
 *
 * <p><b>Thread Safety:</b> This class contains only static utility methods and maintains no state,
 * making it inherently thread-safe.
 *
 * <p><b>Example:</b>
 * <pre>
 * // Get journey name for a tracer
 * String journeyName = Tracers.getJourneyNameForTracers("myProducer", "myMethod");
 * // Returns: "Traced-myProducer.myMethod"
 *
 * // Get call name for a specific trace
 * String callName = Tracers.getCallName(trace);
 * // Returns: "Trace-12345" (where 12345 is the trace ID)
 * </pre>
 *
 * @author lrosenberg
 * @since 23.03.16 01:48
 * @see Tracer
 * @see TracerRepository
 */
public class Tracers {
	public static final String getJourneyNameForTracers(String tracerId){
		return "Traced-"+tracerId;
	}

	public static final String getJourneyNameForTracers(String producerId, String methodName){
		return "Traced-"+TracerRepository.makeKey(producerId, methodName);
	}

	public static final String getCallName(Trace t){
		return "Trace-"+ t.getId();
	}
}
