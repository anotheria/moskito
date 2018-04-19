package net.anotheria.moskito.core.config.dashboards;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Configuration holder for a single chart in a dashboard.
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class ChartConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 8677290973596785948L;


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
		if (this.accumulators != null)
			Arrays.sort(this.accumulators);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ChartConfig)) return false;
		ChartConfig that = (ChartConfig) o;
		return Objects.equals(getCaption(), that.getCaption()) &&
				Arrays.equals(getAccumulators(), that.getAccumulators());
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(getCaption());
		result = 31 * result + Arrays.hashCode(getAccumulators());
		return result;
	}
}
