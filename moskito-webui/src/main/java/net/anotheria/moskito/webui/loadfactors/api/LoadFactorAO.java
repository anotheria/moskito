package net.anotheria.moskito.webui.loadfactors.api;

import net.anotheria.moskito.core.config.loadfactors.LoadFactorConfiguration;
import net.anotheria.util.NumberUtils;

/**
 * Represents a single load factor. A load factor is mathematic dependence between two groups of producers, where we
 * _think_ that the left group triggers traffic to the right group, hence we try to reduce the a) impact of left group on right group or
 * reduce the traffic on left group in order to reduce traffic on right group.
 *
 * @author lrosenberg
 * @since 22.07.20 16:40
 */
public class LoadFactorAO {
	/**
	 * Configuration this object is based on.
	 */
	private LoadFactorConfiguration configuration;
	/**
	 * Ratio between load on the right and requests on the left.
	 */
	private double ratio;
	/**
	 * Number of producers of this type on the left side.
	 */
	private int leftNumberOfProducers;

	/**
	 * Number of producers of this type on the right side.
	 */
	private int rightNumberOfProducers;

	/**
	 * Value for left side.
	 */
	private double leftValue;
	/**
	 * Value for left side.
	 */
	private double rightValue;

	public LoadFactorAO(LoadFactorConfiguration lfConfig) {
		this.configuration = lfConfig;
	}

	public String getName(){
		return configuration.getName();
	}

	public String getInterval(){
		return configuration.getInterval();
	}

	public String getMetric(){
		return configuration.getMetric();
	}

	public String getLeftDescription(){
		return configuration.getLeft().describe() +" ("+leftNumberOfProducers+")";
	}

	public String getRightDescription(){
		return configuration.getRight().describe()+" ("+rightNumberOfProducers+")";
	}

	public int getLeftNumberOfProducers() {
		return leftNumberOfProducers;
	}

	public void setLeftNumberOfProducers(int leftNumberOfProducers) {
		this.leftNumberOfProducers = leftNumberOfProducers;
	}

	public int getRightNumberOfProducers() {
		return rightNumberOfProducers;
	}

	public void setRightNumberOfProducers(int rightNumberOfProducers) {
		this.rightNumberOfProducers = rightNumberOfProducers;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public double getLeftValue() {
		return leftValue;
	}

	public void setLeftValue(double leftValue) {
		this.leftValue = leftValue;
	}

	public double getRightValue() {
		return rightValue;
	}

	public void setRightValue(double rightValue) {
		this.rightValue = rightValue;
	}

	public String getRatioDescription(){
		return ""+ rightValue + " / " + leftValue;
	}

	public String getRatioAsString(){
		return ""+NumberUtils.fractionRound(ratio, 2);
	}
}
