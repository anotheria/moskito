package net.anotheria.moskito.webui.loadfactors.api;

import net.anotheria.moskito.core.config.loadfactors.LoadFactorConfiguration;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:40
 */
public class LoadFactorAO {
	private LoadFactorConfiguration configuration;
	private double ratio;
	private int leftNumberOfProducers;
	private int rightNumberOfProducers;

	private double leftValue;
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
}
