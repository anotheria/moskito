package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * Abstract class for all builtin producers.
 * @author lrosenberg
 *
 */
abstract class AbstractBuiltInProducer<S extends IStats> implements IStatsProducer<S>{

	/**
	 * Name for the builtin system registration.
	 */
	public static final String SUBSYSTEM_BUILTIN = "builtin";

	@Override public String toString(){
		return getClass().getSimpleName();
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}


}
