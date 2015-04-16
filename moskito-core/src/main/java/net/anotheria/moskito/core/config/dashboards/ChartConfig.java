package net.anotheria.moskito.core.config.dashboards;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration holder for a single chart in a dashboard.
 */
@ConfigureMe
public class ChartConfig implements Serializable{

	/**
	 * Chart caption.
	 */
	@Configure
	private String caption;

	/**
	 * Referenced accumulators.
	 */
	@Configure
	private String[] accumulators;

	public String[] getAccumulators() {
		return accumulators;
	}

	public void setAccumulators(String[] accumulators) {
		this.accumulators = accumulators;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public String toString() {
		return "ChartConfig{" +
				"accumulators=" + Arrays.toString(accumulators) +
				", caption='" + caption + '\'' +
				'}';
	}
}
