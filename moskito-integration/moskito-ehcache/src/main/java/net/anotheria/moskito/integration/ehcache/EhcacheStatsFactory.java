package net.anotheria.moskito.integration.ehcache;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;

/**
 * Factory for {@link EhcacheStats}.
 *
 * @author Vladyslav Bezuhlyi
 *
 * @see IOnDemandStatsFactory
 * @see EhcacheStats
 */
public class EhcacheStatsFactory implements IOnDemandStatsFactory<EhcacheStats> {

    @Override
    public EhcacheStats createStatsObject(String name) {
        return new EhcacheStats(name);
    }

}
