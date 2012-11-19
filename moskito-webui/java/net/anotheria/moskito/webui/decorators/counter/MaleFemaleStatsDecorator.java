package net.anotheria.moskito.webui.decorators.counter;

import net.anotheria.moskito.core.counter.MaleFemaleStats;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 12:31
 */
public class MaleFemaleStatsDecorator extends GenericCounterDecorator{
	private static final String CAPTIONS[] = {
			"Male", "Female",
	};

	private static final String SHORT_EXPLANATIONS[] = {
			"Male", "Female",
	};

	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count for male users",
			"Number of calls, clicks, payments - whatever you wanted to count for female users",
	};

	private static final MaleFemaleStats PATTERN = new MaleFemaleStats("pattern");

	public MaleFemaleStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}