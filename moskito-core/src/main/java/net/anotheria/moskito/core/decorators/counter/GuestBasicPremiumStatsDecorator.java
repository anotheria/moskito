package net.anotheria.moskito.core.decorators.counter;

import net.anotheria.moskito.core.counter.GuestBasicPremiumStats;

/**
 * Decorator for guest/basic/premium traffic counter.
 *
 * @author lrosenberg
 * @since 19.11.12 12:32
 */
public class GuestBasicPremiumStatsDecorator extends GenericCounterDecorator{
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
			"Guest", "Basic", "Premium"
	};

	/**
	 * Short explanations.
	 */
	private static final String SHORT_EXPLANATIONS[] = CAPTIONS;

	/**
	 * Explanations.
	 */
	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count for guest users",
			"Number of calls, clicks, payments - whatever you wanted to count for basic users",
			"Number of calls, clicks, payments - whatever you wanted to count for premium users",
	};

	/**
	 * Pattern.
	 */
	private static final GuestBasicPremiumStats PATTERN = new GuestBasicPremiumStats("pattern");

	/**
	 * Creates new GuestBasicPremiumStatsDecorator object.
	 */
	public GuestBasicPremiumStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}