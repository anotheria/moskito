package net.anotheria.moskito.core.config.dashboards;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration holder for a single chart in a dashboard.
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
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

	public String buildCaption() {
		StringBuilder captionBuilder = new StringBuilder();

		int numAccumulators = accumulators.length;
		for (int i = 0; i < numAccumulators; i++) {
			String accumulator = accumulators[i];
			captionBuilder.append(accumulator);
			if (i != numAccumulators - 1) {
				captionBuilder.append(' ');
			}
		}
		return captionBuilder.toString();
	}
}
