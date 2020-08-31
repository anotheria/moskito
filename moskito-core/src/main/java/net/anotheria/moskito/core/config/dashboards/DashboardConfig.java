package net.anotheria.moskito.core.config.dashboards;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(DashboardConfig.class);

	/**
	 * Name of the dashboard.
	 */
	@Configure
	private String name;

	/**
	 * Dashboard refresh rate.
	 * In seconds.
	 * Default value: 60 seconds.
	 */
	private int refresh = 60;

	/**
	 * Charts to be shown on this dashboard.
	 */
	@Configure
	private ChartConfig [] charts;

	/**
	 * Configured chart patterns
	 */
	@Configure
	private ChartPattern [] chartPatterns;

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

	/**
	 * Producer names that should be present on dashboard.
	 */
	@Configure
	private String[] producers;

	/**
	 * All producers which names matches the pattern should be present on dashboard
	 */
	@Configure
	private String[] producerNamePatterns;

	/**
	 * Stores compiled producerNamePatterns
	 */
	private Pattern[] patterns;

	/**
	 * Widgets that should be present on dashboard.
	 * Order in array determines display order on dashboard.
	 */
	@Configure
	private String[] widgets = new String[] {
			DashboardWidget.THRESHOLDS.getName(), DashboardWidget.GAUGES.getName(),
			DashboardWidget.CHARTS.getName(), DashboardWidget.PRODUCERS.getName()
	};


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("DashboardConfig{");
		sb.append("name='").append(name).append('\'');
		sb.append(", refresh=").append(refresh);
		sb.append(", charts=").append(Arrays.toString(charts));
		sb.append(", chartPatterns=").append(Arrays.toString(chartPatterns));
		sb.append(", thresholds=").append(Arrays.toString(thresholds));
		sb.append(", gauges=").append(Arrays.toString(gauges));
		sb.append(", producers=").append(Arrays.toString(producers));
		sb.append(", producerNamePatterns=").append(Arrays.toString(producerNamePatterns));
		sb.append(", widgets=").append(Arrays.toString(widgets));
		sb.append('}');
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
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

	public ChartPattern[] getChartPatterns() {
		return chartPatterns;
	}

	public void setChartPatterns(ChartPattern[] chartPatterns) {
		this.chartPatterns = chartPatterns;

		if (chartPatterns != null && chartPatterns.length > 0) {
			for (ChartPattern chartPattern : chartPatterns) {
				List<Pattern> somePatterns = new LinkedList<>();
				for (String accumulatorPattern : chartPattern.getAccumulatorPatterns()) {
					try {
						somePatterns.add(Pattern.compile(accumulatorPattern));
					} catch (PatternSyntaxException e) {
						log.warn("Couldn't compile pattern: \"" + accumulatorPattern + "\"", e);
					}
				}
				chartPattern.setPatterns(somePatterns.toArray(new Pattern[somePatterns.size()]));
			}
		}
	}

	public String[] getGauges() {
		return gauges;
	}

	public void setGauges(String[] gauges) {
		this.gauges = gauges;
	}

	public String[] getProducers() {
		return producers;
	}

	public void setProducers(String[] producers) {
		this.producers = producers;
	}

	public String[] getProducerNamePatterns() {
		return producerNamePatterns;
	}

	public void setProducerNamePatterns(String[] producerNamePatterns) {
		this.producerNamePatterns = producerNamePatterns;

		if (producerNamePatterns != null && producerNamePatterns.length > 0) {
			List<Pattern> somePatterns = new LinkedList<>();

			for (String producerNamePattern : getProducerNamePatterns()) {
				try {
					somePatterns.add(Pattern.compile(producerNamePattern));
				} catch (PatternSyntaxException e) {
					log.warn("Couldn't compile pattern: \"" + producerNamePattern + "\"", e);
				}
			}
			setPatterns(somePatterns.toArray(new Pattern[somePatterns.size()]));
		}
	}

	public Pattern[] getPatterns() {
		return patterns;
	}

	public void setPatterns(Pattern[] patterns) {
		this.patterns = patterns;
	}

	public String[] getWidgets() {
		return widgets;
	}

	public void setWidgets(String[] widgets) {
		this.widgets = widgets;
	}

	public boolean containsGauge(String gaugeName) {
		return listContainsElement(gauges, gaugeName);
	}

	public boolean containsChart(String chartName) {
		if (charts==null || charts.length==0)
			return false;
		for (ChartConfig cc : charts){
			if (cc.getCaption()!=null && cc.getCaption().equals(chartName))
				return true;
		}
		return false;
	}

	public boolean containsThreshold(String thresholdName) {
		return listContainsElement(thresholds, thresholdName);
	}

	private boolean listContainsElement(String[] list, String aName) {
		if (list==null || list.length==0)
			return false;
		for (String e : list){
			if (e.equals(aName))
				return true;
		}
		return false;
	}

}
