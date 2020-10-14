package net.anotheria.moskito.core.config;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.core.config.errorhandling.ErrorHandlingConfig;
import net.anotheria.moskito.core.config.filter.FilterConfig;
import net.anotheria.moskito.core.config.gauges.GaugesConfig;
import net.anotheria.moskito.core.config.journey.JourneyConfig;
import net.anotheria.moskito.core.config.loadfactors.LoadFactorsConfiguration;
import net.anotheria.moskito.core.config.plugins.PluginsConfig;
import net.anotheria.moskito.core.config.producers.BuiltinProducersConfig;
import net.anotheria.moskito.core.config.producers.MBeanProducerConfig;
import net.anotheria.moskito.core.config.producers.TomcatRequestProcessorProducerConfig;
import net.anotheria.moskito.core.config.tagging.TaggingConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsAlertsConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;
import net.anotheria.moskito.core.config.tracing.TracingConfiguration;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.tracer.TracerRepository;
import net.anotheria.moskito.core.util.AfterStartTasks;
import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class contains complete moskito configuration at runtime. It is configured with ConfigureMe, but can be altered
 * programmatically. Use MoskitoConfigurationHolder.getConfiguration/setConfiguration to access/alter this class.
 *
 * @author lrosenberg
 * @since 22.10.12 15:59
 */
@SuppressFBWarnings({"SE_BAD_FIELD", "EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@ConfigureMe(name="moskito")
public class MoskitoConfiguration implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 6636333765192447227L;

	@Configure
	private String applicationName = "";

	@Configure
	private String[] intervals = new String[]{
			"1m",
			"5m",
			"15m",
			"1h",
			"12h",
			"1d",
			"snapshot"
	};

	/**
	 * Always have a disabled kill switch configured.
	 */
	@Configure
	@SerializedName("@killSwitch")
	private KillSwitchConfiguration killSwitch = new KillSwitchConfiguration();

	/**
	 * This variable is filled after configuration with intervals configured via the intervals variable.
	 */
	@DontConfigure
	private transient volatile Interval[] configuredIntervals;

	/**
	 * Config object for alerting.
	 */
	@Configure
	@SerializedName("@thresholdsAlertsConfig")
	private ThresholdsAlertsConfig thresholdsAlertsConfig = new ThresholdsAlertsConfig();

	/**
	 * Config object for thresholds.
	 */
	@Configure
	@SerializedName("@thresholdsConfig")
	private ThresholdsConfig thresholdsConfig = new ThresholdsConfig();

	/**
	 * Config object for gauges.
	 */
	@Configure
	@SerializedName("@gaugesConfig")
	private GaugesConfig gaugesConfig = new GaugesConfig();

	/**
	 * Config object for dashboards.
	 */
	@Configure
	@SerializedName("@dashboardsConfig")
	private DashboardsConfig dashboardsConfig = new DashboardsConfig();

	/**
	 * Configuration object for load factors (beta).
	 */
	@Configure
	@SerializedName("@loadFactorsConfig")
	private LoadFactorsConfiguration loadFactorsConfig = new LoadFactorsConfiguration();


		/**
	 * Config object for accumulators.
	 */
	@Configure
	@SerializedName("@accumulatorsConfig")
	private AccumulatorsConfig accumulatorsConfig = new AccumulatorsConfig();

	/**
	 * Config objects for plugins.
	 */
	@Configure
	@SerializedName("@pluginsConfig")
	private PluginsConfig pluginsConfig = new PluginsConfig();

    /**
     * Config object for generic MBean producers.
     */
    @Configure
    @SerializedName("@mbeanProducersConfig")
    private MBeanProducerConfig mbeanProducersConfig = new MBeanProducerConfig();

	/**
	 * Configuration for builtin producers. Allows to switch off builtin producers.
	 */
	@Configure
	@SerializedName("@builtinProducersConfig")
	private BuiltinProducersConfig builtinProducersConfig = new BuiltinProducersConfig();

	/**
	 * Config object for tomcat GlobalRequestProcessor producer.
	 */
	@Configure
	@SerializedName("@tomcatRequestProcessorProducerConfig")
	private TomcatRequestProcessorProducerConfig tomcatRequestProcessorProducerConfig = new TomcatRequestProcessorProducerConfig();

	/**
	 * Configuration for tracing.
	 */
	@Configure
	@SerializedName("@tracingConfig")
	private TracingConfiguration tracingConfig = new TracingConfiguration();

	/**
	 * Configuration for journey handling.
	 */
	@Configure
	@SerializedName("@journeyConfig")
	private JourneyConfig journeyConfig = new JourneyConfig();

	/**
	 * Configuration for error config. It configures the behaviour of the built in error producer.
	 */
	@Configure
	@SerializedName("@errorHandlingConfig")
	private ErrorHandlingConfig errorHandlingConfig = new ErrorHandlingConfig();

	/**
	 * Configuration of generic filters.
	 */
	@Configure
	@SerializedName("@filterConfig")
	private FilterConfig filterConfig = new FilterConfig();

	@Configure
	@SerializedName("@taggingConfig")
	private TaggingConfig taggingConfig = new TaggingConfig();


	public ThresholdsAlertsConfig getThresholdsAlertsConfig() {
		return thresholdsAlertsConfig;
	}

	public void setThresholdsAlertsConfig(ThresholdsAlertsConfig thresholdsAlertsConfig) {
		this.thresholdsAlertsConfig = thresholdsAlertsConfig;
	}

	public ThresholdsConfig getThresholdsConfig() {
		return thresholdsConfig;
	}

	public void setThresholdsConfig(ThresholdsConfig thresholds) {
		this.thresholdsConfig = thresholds;
	}

	@Override public String toString(){
		return "thresholdsAlertsConfig: "+thresholdsAlertsConfig+", thresholds: "+thresholdsConfig+", accumulators:" +accumulatorsConfig+", gauges: "+gaugesConfig+", dashboards: "+dashboardsConfig+", errorHandling: "+errorHandlingConfig;
	}

	public AccumulatorsConfig getAccumulatorsConfig() {
		return accumulatorsConfig;
	}

	public void setAccumulatorsConfig(AccumulatorsConfig accumulatorsConfig) {
		this.accumulatorsConfig = accumulatorsConfig;
	}

	public PluginsConfig getPluginsConfig() {
		return pluginsConfig;
	}

	public void setPluginsConfig(PluginsConfig pluginsConfig) {
		this.pluginsConfig = pluginsConfig;
	}

    /**
     * @return {@link #mbeanProducersConfig}
     */
    public MBeanProducerConfig getMbeanProducersConfig() {
        return mbeanProducersConfig;
    }

    /**
     * @param mbeanProducersConfig
     *            new {@link MBeanProducerConfig} setup.
     */
    public void setMbeanProducersConfig(MBeanProducerConfig mbeanProducersConfig) {
        this.mbeanProducersConfig = mbeanProducersConfig;
    }

	public BuiltinProducersConfig getBuiltinProducersConfig() {
		return builtinProducersConfig;
	}

	public void setBuiltinProducersConfig(BuiltinProducersConfig builtinProducersConfig) {
		this.builtinProducersConfig = builtinProducersConfig;
	}

	public TomcatRequestProcessorProducerConfig getTomcatRequestProcessorProducerConfig() {
		return tomcatRequestProcessorProducerConfig;
	}

	public void setTomcatRequestProcessorProducerConfig(TomcatRequestProcessorProducerConfig tomcatRequestProcessorProducerConfig) {
		this.tomcatRequestProcessorProducerConfig = tomcatRequestProcessorProducerConfig;
	}

	public GaugesConfig getGaugesConfig() {
		return gaugesConfig;
	}

	public void setGaugesConfig(GaugesConfig gaugesConfig) {
		this.gaugesConfig = gaugesConfig;
	}

	public DashboardsConfig getDashboardsConfig() {
		return dashboardsConfig;
	}

	public void setDashboardsConfig(DashboardsConfig dashboardsConfig) {
		this.dashboardsConfig = dashboardsConfig;
	}

	public TracingConfiguration getTracingConfig() {
		return tracingConfig;
	}

	public void setTracingConfig(TracingConfiguration tracingConfig) {
		this.tracingConfig = tracingConfig;
	}

	public JourneyConfig getJourneyConfig() {
		return journeyConfig;
	}

	public void setJourneyConfig(JourneyConfig journeyConfig) {
		this.journeyConfig = journeyConfig;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public ErrorHandlingConfig getErrorHandlingConfig() {
		return errorHandlingConfig;
	}

	public void setErrorHandlingConfig(ErrorHandlingConfig errorHandlingConfig) {
		this.errorHandlingConfig = errorHandlingConfig;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public TaggingConfig getTaggingConfig() {
		return taggingConfig;
	}

	public void setTaggingConfig(TaggingConfig taggingConfig) {
		this.taggingConfig = taggingConfig;
	}

	public String[] getIntervals() {
		return intervals;
	}

	public void setIntervals(String[] intervals) {
		this.intervals = intervals;
	}

	@AfterConfiguration
	public void createIntervalsAfterConfiguration(){
		createIntervals();
		//to prevent errorHandlingConfig to be initialized BEFORE intervals are and prevent recursive calls, call it manually

		AfterStartTasks.submitTask(new Runnable() {
			@Override
			public void run() {
				if (errorHandlingConfig!=null)
					errorHandlingConfig.afterConfiguration();
			}
		});
	}

	@AfterConfiguration public void executeAfterConfigurationTasks(){
		AfterStartTasks.submitTask(new Runnable() {
			@Override
			public void run() {
				//check if there are any tracers already configured (fix for https://github.com/anotheria/moskito/issues/235)
				String[] configuredTracers = getTracingConfig().getTracers();
				if (configuredTracers!=null && configuredTracers.length!=0){
					for (String producerId : configuredTracers){
						TracerRepository.getInstance().enableTracingForProducerId(producerId);
					}
				}
			}
		});

	}

	private void createIntervals(){
    	if (intervals==null || intervals.length==0)
    		throw new IllegalStateException("Can't run moskito without intervals at all");
    	String[] toConfigure = intervals; //copy variable so it won't be changed within the call.
    	Interval[] newConfiguredIntervals = new Interval[toConfigure.length];
    	for (int i=0; i<toConfigure.length; i++){
    		Interval newInterval = IntervalRegistry.getInstance().getInterval(toConfigure[i]);
    		newConfiguredIntervals[i] = newInterval;
		}
    	configuredIntervals = newConfiguredIntervals;
	}

	public Interval[] getConfiguredIntervals(){
    	if (configuredIntervals==null){
    		createIntervals();
		}
    	return configuredIntervals.clone();
	}

	public List<String> getConfiguredIntervalNames(){
    	if (intervals==null || intervals.length==0)
    		return Collections.EMPTY_LIST;
    	return Arrays.asList(intervals);
	}

	public KillSwitchConfiguration getKillSwitch() {
		return killSwitch;
	}

	public void setKillSwitch(KillSwitchConfiguration killSwitch) {
		this.killSwitch = killSwitch;
	}

	public LoadFactorsConfiguration getLoadFactorsConfig() {
		return loadFactorsConfig;
	}

	public void setLoadFactorsConfig(LoadFactorsConfiguration loadFactorsConfig) {
		this.loadFactorsConfig = loadFactorsConfig;
	}
}


