package net.java.dev.moskito.core.helper;

import net.java.dev.moskito.core.producers.IStats;

/**
 * Describes something that can be tied to an interval. This is used for accumulators and thresholds.
 * @author lrosenberg
 *
 */
public interface Tieable extends IntervalUpdateable{
	/**
	 * Retuns true if this tieable has been activated.
	 * @return
	 */
	boolean isActivated();
	/**
	 * Returns the name of the target stats.
	 * @return
	 */
	Object getTargetStatName();
	/**
	 * Tie to a concreate stats object.
	 * @param s
	 */
	void tieToStats(IStats s);
	/**
	 * Returns the definition of this tieable.
	 * @return
	 */
	TieableDefinition getDefinition();
	/**
	 * Returns the name of this tieable for debug/presentation purposes.
	 * @return
	 */
	String getName();

	/**
	 * Returns internal id for further operations (CRUD).
	 * @return internal incremental id.
	 */
	String getId();

}
