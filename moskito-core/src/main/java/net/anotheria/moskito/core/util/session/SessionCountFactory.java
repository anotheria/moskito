package net.anotheria.moskito.core.util.session;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.stats.Interval;

/**
 *  {@link SessionCountStats} factory.
 *
 * @author lrosenberg
 * @since 26.04.13 23:55
 */
public class SessionCountFactory extends AbstractStatsFactory<SessionCountStats> {

	/**
	 * Default instance to spare additional object creation.
	 */
	public static final AbstractStatsFactory<SessionCountStats> DEFAULT_INSTANCE = new SessionCountFactory();

	/**
	 * Creates a new factory with custom intervals.
	 *
	 * @param configuredIntervals
	 * 		{@link Interval} array
	 */
	public SessionCountFactory(final Interval[] configuredIntervals){
		super(configuredIntervals == null || configuredIntervals.length <= 0 ? Constants.getDefaultIntervals() : configuredIntervals);
	}

	/**
	 * Createsa new factory with default intervals.
	 */
	public SessionCountFactory(){
		super();
	}

	@Override public SessionCountStats createStatsObject(String name) {
		return new SessionCountStats(name, getIntervals());
	}
}