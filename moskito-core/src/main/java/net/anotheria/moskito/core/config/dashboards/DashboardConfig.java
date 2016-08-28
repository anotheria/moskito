package net.anotheria.moskito.core.config.dashboards;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration of a single Dashboard.
 *
 * @author lrosenberg
 * @since 12.02.15 00:58
 */
@ConfigureMe
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class DashboardConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5472953727159931087L;

	/**
	 * Name of the dashboard.
	 */
	@Configure
	private String name;

	/**
	 * Charts to be shown on this dashboard.
	 */
	@Configure
	private ChartConfig [] charts;

	/**
	 * Names of the thresholds that should be on that dashboard.
	 */
	@Configure
	private String [] thresholds;

	/**
	 * Names of the gauges that should be on that dashboard.
	 */
	@Configure
	private String [] gauges;

	@Override
	public String toString() {
		return "DashboardConfig{" +
				"charts=" + Arrays.toString(charts) +
				", name='" + name + '\'' +
				", thresholds=" + Arrays.toString(thresholds) +
				", gauges=" + Arrays.toString(gauges) +
				'}';
	}

	public String[] getGauges() {
		return gauges;
	}

	public void setGauges(String[] gauges) {
		this.gauges = gauges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getThresholds() {
		return thresholds;
	}

	public void setThresholds(String[] thresholds) {
		this.thresholds = thresholds;
	}

	public ChartConfig[] getCharts() {
		return charts;
	}

	public void setCharts(ChartConfig[] charts) {
		this.charts = charts;
	}
}
