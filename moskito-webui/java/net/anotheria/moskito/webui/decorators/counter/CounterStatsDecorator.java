package net.anotheria.moskito.webui.decorators.counter;

import net.anotheria.moskito.core.counter.CounterStats;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 12:01
 */
public class CounterStatsDecorator extends GenericCounterDecorator{
	private static final String CAPTIONS[] = {
			"Count",
	};

	private static final String SHORT_EXPLANATIONS[] = {
			"Counter",
	};

	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count",
	};

	private static final CounterStats PATTERN = new CounterStats("pattern");

	public CounterStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}
