package net.anotheria.moskito.core.helper;

/**
 * This interface describes something that can be updated when an interval is updated. This is used for accumulators and thresholds allowing
 * them to react to interval updates without explicitely tie-ing themself to interval updates.
 */
public interface IntervalUpdateable {
	void update();
}
