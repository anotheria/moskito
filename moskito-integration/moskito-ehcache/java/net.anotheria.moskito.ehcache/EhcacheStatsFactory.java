package net.anotheria.moskito.ehcache;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;

/**
 * Factory for {@link net.anotheria.moskito.ehcache.EhcacheStats}.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory
 * @see net.anotheria.moskito.ehcache.EhcacheStats
 */
public class EhcacheStatsFactory implements IOnDemandStatsFactory<EhcacheStats> {

    @Override
    public EhcacheStats createStatsObject(String name) {
        return new EhcacheStats(name);
    }

}
