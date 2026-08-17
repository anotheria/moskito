package net.anotheria.moskito.core.topproducers;

/**
 * A category is a single dimension a producer can be ranked by (number of requests, total time spent, errors, etc).
 * Each category knows the name of the value it ranks by inside a {@link net.anotheria.moskito.core.predefined.RequestOrientedStats}
 * and how to extract that value out of its string representation.
 *
 * @author lrosenberg
 * @since 20.05.16 18:51
 */
public enum Category {
	/**
	 * Ranks producers by the number of requests.
	 */
	REQUESTS("req"),
	/**
	 * Ranks producers by the total time spent.
	 */
	TOTAL_TIME("time"),
	/**
	 * Ranks producers by the number of errors.
	 */
	ERRORS("err"),
	/**
	 * Ranks producers by the maximum number of concurrent requests. Note that the sum of the peaks of all producers is
	 * an upper bound of the peak of the system rather than an exact total, the share of a producer in this category is
	 * therefore an approximation.
	 */
	MAX_CONCURRENT_REQUEST("mcr"),
	/**
	 * Ranks producers by their error rate. The rate is a floating point value, therefore it is multiplied by 100 to
	 * be kept as a long score.
	 */
	ERROR_RATE("errorrate") {
		@Override
		public long extractValue(String valueAsString) {
			return (long) (Double.parseDouble(valueAsString) * 100);
		}

		/**
		 * The error rate is not additive: the sum of the error rates of all producers is a meaningless number. Unlike
		 * the other categories the raw value already is a rate on a fixed scale - percent with two decimals, which
		 * {@link #extractValue(String)} turns into basis points - and is therefore its own share score. A producer
		 * failing every single request scores {@link #SHARE_SCALE} regardless of what the other producers do.
		 */
		@Override
		public long share(long value, long sumOfValues) {
			return value;
		}
	};

	/**
	 * Scale of the share score: a producer accounting for everything that happened in a category during an interval
	 * scores SHARE_SCALE, so the score is expressed in basis points (hundredth of a percent).
	 */
	public static final long SHARE_SCALE = 10_000L;

	/**
	 * Name of the value in the stats object this category ranks by.
	 */
	private final String valueName;

	Category(String aValueName) {
		valueName = aValueName;
	}

	/**
	 * Returns the name of the value in the stats object this category ranks by.
	 * @return the value name.
	 */
	public String getValueName() {
		return valueName;
	}

	/**
	 * Extracts the ranking value out of its string representation. Overridden by categories whose value isn't a plain long.
	 * @param valueAsString the value as returned by the stats object.
	 * @return the value as long.
	 */
	public long extractValue(String valueAsString) {
		return Long.parseLong(valueAsString);
	}

	/**
	 * Turns the raw value of a producer into its share score for one interval: how much of everything the ranked
	 * producers did in this category during that interval belongs to this producer, in basis points.
	 * <p>
	 * The sum is used as the reference, not the maximum. The maximum is the least robust value of the sample, a single
	 * producer spiking for one interval would push everybody else towards zero, and being the heaviest producer would
	 * score the same whether that means 20% or 99% of the load. Normalizing by the sum instead means every interval
	 * distributes the same {@link #SHARE_SCALE} points among the producers, which keeps the scores of different
	 * intervals comparable and therefore accumulatable.
	 * <p>
	 * Overridden by categories whose value is not additive.
	 *
	 * @param value the raw value of the producer in this interval.
	 * @param sumOfValues the sum of the raw values of all ranked producers in this category in this interval.
	 * @return the share of the producer in basis points.
	 */
	public long share(long value, long sumOfValues) {
		//double math on purpose, value * SHARE_SCALE would overflow for large total times.
		return sumOfValues <= 0 ? 0 : Math.round((double) value / (double) sumOfValues * SHARE_SCALE);
	}
}
