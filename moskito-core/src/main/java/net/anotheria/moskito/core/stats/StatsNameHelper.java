package net.anotheria.moskito.core.stats;

import java.lang.reflect.Method;

/**
 * Helper for {@link StatsName} stuff.
 */
public final class StatsNameHelper {

    /**
     * Returns name for monitored method.
     *
     * @param method
     *         method to monitor
     * @return method name
     */
    public static String getMethodStatsName(Method method) {
        StatsName statsName = method.getAnnotation(StatsName.class);
        return statsName == null ? method.getName() : statsName.value();
    }

}
