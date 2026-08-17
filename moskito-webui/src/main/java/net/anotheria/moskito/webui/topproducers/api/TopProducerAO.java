package net.anotheria.moskito.webui.topproducers.api;

import java.io.Serializable;

/**
 * Transfer object representing a single producer in the top-producers ranking of one category. It flattens the
 * accumulated ranking score of a producer so it can be transported over the api layer (locally, via rest/mcp or
 * remotely via distributeme).
 *
 * @author lrosenberg
 */
public class TopProducerAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Id of the ranked producer.
	 */
	private String producerId;
	/**
	 * Category of the ranked producer as reported by the producer itself.
	 */
	private String producerCategory;
	/**
	 * Subsystem of the ranked producer.
	 */
	private String producerSubsystem;
	/**
	 * The ranking category (one of {@link net.anotheria.moskito.core.topproducers.Category}) this score refers to.
	 */
	private String rankingCategory;
	/**
	 * Accumulated score over all ranked intervals.
	 */
	private long cumulatedScore;
	/**
	 * Highest single interval score.
	 */
	private long topScore;
	/**
	 * Lowest single interval score.
	 */
	private long bottomScore;
	/**
	 * Score of the last ranked interval.
	 */
	private long lastScore;
	/**
	 * Number of intervals the producer has been ranked in.
	 */
	private long scoreCount;
	/**
	 * Average score per ranked interval.
	 */
	private double averageScore;
	/**
	 * Accumulated share score over all ranked intervals, in basis points (see
	 * {@link net.anotheria.moskito.core.topproducers.Category#SHARE_SCALE}).
	 */
	private long cumulatedShareScore;
	/**
	 * Average share of the producer per ranked interval, in basis points. 10.000 means the producer accounted for
	 * everything that happened in this category.
	 */
	private double averageShareScore;
	/**
	 * Share of the producer in the last ranked interval, in basis points.
	 */
	private long lastShareScore;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getProducerCategory() {
		return producerCategory;
	}

	public void setProducerCategory(String producerCategory) {
		this.producerCategory = producerCategory;
	}

	public String getProducerSubsystem() {
		return producerSubsystem;
	}

	public void setProducerSubsystem(String producerSubsystem) {
		this.producerSubsystem = producerSubsystem;
	}

	public String getRankingCategory() {
		return rankingCategory;
	}

	public void setRankingCategory(String rankingCategory) {
		this.rankingCategory = rankingCategory;
	}

	public long getCumulatedScore() {
		return cumulatedScore;
	}

	public void setCumulatedScore(long cumulatedScore) {
		this.cumulatedScore = cumulatedScore;
	}

	public long getTopScore() {
		return topScore;
	}

	public void setTopScore(long topScore) {
		this.topScore = topScore;
	}

	public long getBottomScore() {
		return bottomScore;
	}

	public void setBottomScore(long bottomScore) {
		this.bottomScore = bottomScore;
	}

	public long getLastScore() {
		return lastScore;
	}

	public void setLastScore(long lastScore) {
		this.lastScore = lastScore;
	}

	public long getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(long scoreCount) {
		this.scoreCount = scoreCount;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public long getCumulatedShareScore() {
		return cumulatedShareScore;
	}

	public void setCumulatedShareScore(long cumulatedShareScore) {
		this.cumulatedShareScore = cumulatedShareScore;
	}

	public double getAverageShareScore() {
		return averageShareScore;
	}

	public void setAverageShareScore(double averageShareScore) {
		this.averageShareScore = averageShareScore;
	}

	public long getLastShareScore() {
		return lastShareScore;
	}

	public void setLastShareScore(long lastShareScore) {
		this.lastShareScore = lastShareScore;
	}

	/**
	 * Returns the average share as a percentage rounded to two decimals, for display purposes. Derived from
	 * {@link #getAverageShareScore()}, therefore not part of the transported state.
	 * @return the average share of this producer in percent.
	 */
	public double getAverageSharePercent() {
		return Math.round(averageShareScore) / 100d;
	}

	@Override
	public String toString() {
		return "TopProducerAO{" +
				"producerId='" + producerId + '\'' +
				", rankingCategory='" + rankingCategory + '\'' +
				", cumulatedScore=" + cumulatedScore +
				", averageScore=" + averageScore +
				'}';
	}
}
