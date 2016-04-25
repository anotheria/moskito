package net.anotheria.moskito.extensions.sampling;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.producers.IStats;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.04.15 23:40
 */
public interface StatsMapper {
	IOnDemandStatsFactory getFactory();
	void updateStats(IStats statsObject, Sample sample);
}
