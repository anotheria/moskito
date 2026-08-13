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
	 * Ranks producers by the maximum number of concurrent requests.
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
	};

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
}
