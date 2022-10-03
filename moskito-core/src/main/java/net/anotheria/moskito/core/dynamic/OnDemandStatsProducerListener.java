package net.anotheria.moskito.core.dynamic;

public interface OnDemandStatsProducerListener {
    /**
     * Called when a new stat name has been registered.
     * @param producer
     * @param statName
     */
    void notifyStatCreated(OnDemandStatsProducer producer, String statName);
}
