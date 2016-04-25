package net.anotheria.moskito.core.decorators.counter;

import net.anotheria.moskito.core.counter.CounterStats;

/**
 * Decorator for counter stats object.
 *
 * @author lrosenberg
 * @since 19.11.12 12:01
 */
public class CounterStatsDecorator extends GenericCounterDecorator{
	/**
	 * Caption(s).
	 */
	private static final String CAPTIONS[] = {
			"Counter",
	};

	/**
	 * Short explanations.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
			"Counter",
	};

	/**
	 * Explanations.
	 */
	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count",
	};

	/**
	 * Pattern.
	 */
	private static final CounterStats PATTERN = new CounterStats("pattern");

	/**
	 * Creates a new counter stats decorator by calling super constructor with pattern, captions, short_explanations and
	 * explanations as parameter.
	 */
	public CounterStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}
