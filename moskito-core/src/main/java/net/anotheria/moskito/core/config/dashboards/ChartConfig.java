package net.anotheria.moskito.core.config.dashboards;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.Arrays;

@ConfigureMe
public class ChartConfig {

	@Configure
	private String caption;

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
