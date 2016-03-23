package net.anotheria.moskito.core.tracer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.16 01:48
 */
public class Tracers {
	public static final String getJourneyNameForTracers(){
		return "Traced";
	}

	public static final String getCallName(Trace t){
		return "Trace-"+ t.getId();
	}
}
