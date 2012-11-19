package net.anotheria.moskito.webui.decorators.counter;

import net.anotheria.moskito.core.counter.GuestBasicPremiumStats;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 12:32
 */
public class GuestBasicPremiumStatsDecorator extends GenericCounterDecorator{
	private static final String CAPTIONS[] = {
			"Guest", "Basic", "Premium"
	};

	private static final String SHORT_EXPLANATIONS[] = CAPTIONS;

	private static final String EXPLANATIONS[] = {
			"Number of calls, clicks, payments - whatever you wanted to count for guest users",
			"Number of calls, clicks, payments - whatever you wanted to count for basic users",
			"Number of calls, clicks, payments - whatever you wanted to count for premium users",
	};

	private static final GuestBasicPremiumStats PATTERN = new GuestBasicPremiumStats("pattern");

	public GuestBasicPremiumStatsDecorator(){
		super(PATTERN, CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}