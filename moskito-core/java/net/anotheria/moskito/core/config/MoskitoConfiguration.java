package net.anotheria.moskito.core.config;

import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.config.plugins.PluginsConfig;
import net.anotheria.moskito.core.config.producers.MBeanProducerConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsAlertsConfig;
import net.anotheria.moskito.core.config.thresholds.ThresholdsConfig;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import com.google.gson.annotations.SerializedName;

/**
 * This class contains complete moskito configuration at runtime. It is configured with configureme, but can be altered
 * programmatically. Use MoskitoConfigurationHolder.getConfiguration/setConfiguration to access/alter this class.
 *
 * @author lrosenberg
 * @since 22.10.12 15:59
 */
@ConfigureMe(name="moskito")
public class MoskitoConfiguration {
	/**
	 * Config object for alerting.
	 */
	@Configure
	@SerializedName("@thresholdsAlertsConfig")
	private ThresholdsAlertsConfig thresholdsAlertsConfig = new ThresholdsAlertsConfig();

	/**
	 * Config object for threshold.
	 */
	@Configure
	@SerializedName("@thresholdsConfig")
	private ThresholdsConfig thresholdsConfig = new ThresholdsConfig();

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
		return "thresholdsAlertsConfig: "+thresholdsAlertsConfig+", thresholds: "+thresholdsConfig+", accumulators:" +accumulatorsConfig;
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

}


