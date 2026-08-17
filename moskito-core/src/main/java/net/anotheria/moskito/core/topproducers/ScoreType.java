package net.anotheria.moskito.core.topproducers;

/**
 * Selects which of the two scores a producer collects per interval the ranking is based on. Both are maintained at the
 * same time, they answer different questions and neither replaces the other.
 *
 * @author lrosenberg
 */
public enum ScoreType {
	/**
	 * The position based score: a producer collects the number of producers it outranked in the interval. Robust
	 * against outliers, since it only uses the order of the producers and not their values, and it keeps ranking the
	 * small producers among each other. It cannot express magnitude though - a producer with a million requests scores
	 * exactly one point more than the one with ten thousand.
	 */
	ORDINAL {
		@Override
		public ProducerEntryValue getValue(ProducerEntry entry, Category category) {
			return entry.getValue(category);
		}
	},
	/**
	 * The magnitude based score: a producer collects its share of everything that happened in the category during the
	 * interval, in basis points (see {@link Category#share(long, long)}). Answers how much of the system a producer
	 * actually accounts for, which is what bounds the gain of optimizing it. The flip side is that it flattens
	 * everything outside the top few producers towards zero.
	 */
	SHARE {
		@Override
		public ProducerEntryValue getValue(ProducerEntry entry, Category category) {
			return entry.getShareValue(category);
		}
	};

	/**
	 * Returns the accumulated value of this score type for the given producer and ranking category, or null if the
	 * producer was never ranked in it.
	 * @param entry the producer entry to read from.
	 * @param category the ranking category.
	 * @return the accumulated value or null.
	 */
	public abstract ProducerEntryValue getValue(ProducerEntry entry, Category category);
}
