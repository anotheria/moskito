package net.anotheria.moskito.core.config.accumulators;

/**
 * Mode for accumulator selection in an accumulator set.
 *
 * @author lrosenberg
 * @since 14.01.15 10:09
 */
public enum AccumulatorSetMode {
	/**
	 * Combined, all in one chart.
	 */
	COMBINED,
	/**
	 * Combined, all in one chart and normalized by 100 (each value is a percentage value to its relative max).
	 */
	NORMALIZED,
	/**
	 * Multiple charts, every chart for itself.
	 */
	MULTIPLE;
}
