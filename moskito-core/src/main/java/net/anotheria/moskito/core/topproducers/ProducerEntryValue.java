package net.anotheria.moskito.core.topproducers;

/**
 * Holds the accumulated ranking score of a single producer in a single {@link Category}. The score is added up over
 * time (once per interval update), so besides the cumulated score it also keeps track of the top, bottom, last and
 * average score to allow different views on how a producer ranked historically.
 *
 * @author lrosenberg
 * @since 25.05.16 23:26
 */
public class ProducerEntryValue {
	/**
	 * Sum of all scores this producer collected in this category.
	 */
	private long cumulatedScore = 0;
	/**
	 * Highest single score.
	 */
	private long topScore = Long.MIN_VALUE;
	/**
	 * Lowest single score.
	 */
	private long bottomScore = Long.MAX_VALUE;
	/**
	 * Number of scores added, i.e. number of intervals this producer was ranked.
	 */
	private long scoreCount = 0;
	/**
	 * Last added score.
	 */
	private long lastScore = 0;

	/**
	 * Adds a new score for one interval and updates all derived values.
	 * @param value the score for this interval.
	 */
	public void addScore(long value) {
		cumulatedScore += value;
		scoreCount++;
		topScore = value > topScore ? value : topScore;
		bottomScore = value < bottomScore ? value : bottomScore;
		lastScore = value;
	}

	public long getBottomScore() {
		return bottomScore;
	}

	public long getCumulatedScore() {
		return cumulatedScore;
	}

	public long getScoreCount() {
		return scoreCount;
	}

	public long getTopScore() {
		return topScore;
	}

	public long getLastScore() {
		return lastScore;
	}

	public double getAverageScore() {
		return scoreCount > 0 ? (double) cumulatedScore / scoreCount : 0D;
	}

	@Override
	public String toString() {
		return "ProducerEntryValue{" +
				"bottomScore=" + bottomScore +
				", cumulatedScore=" + cumulatedScore +
				", topScore=" + topScore +
				", scoreCount=" + scoreCount +
				", lastScore=" + lastScore +
				", avg=" + getAverageScore() +
				'}';
	}
}
