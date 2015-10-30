package net.anotheria.moskito.core.decorators.counter;

import net.anotheria.moskito.core.counter.MaleFemaleStats;

/**
 * Decorator for male/female separator counter.
 *
 * @author lrosenberg
 * @since 19.11.12 12:31
 */
public class MaleFemaleStatsDecorator extends GenericCounterDecorator{
	/**
	 * Caption.
	 */
	private static final String CAPTIONS[] = {
			"Male", "Female",
	};

	/**
	 * Short explanations.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
			"Male", "Female",
	};

	/**
	 * Explanations.
	 */
	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count for male users",
			"Number of calls, clicks, payments - whatever you wanted to count for female users",
	};

	/**
	 * Pattern.
	 */
	private static final MaleFemaleStats PATTERN = new MaleFemaleStats("pattern");

	/**
	 * Creates new MaleFemaleStatsDecorator object.
	 */
	public MaleFemaleStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}