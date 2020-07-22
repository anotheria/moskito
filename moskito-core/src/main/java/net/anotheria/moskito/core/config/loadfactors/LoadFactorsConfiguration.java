package net.anotheria.moskito.core.config.loadfactors;

import com.google.gson.annotations.SerializedName;
import org.configureme.annotations.Configure;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:04
 */
public class LoadFactorsConfiguration {
	/**
	 * Configured gauges.
	 */
	@Configure
	@SerializedName("@factors")
	private LoadFactorConfiguration[] factors = new LoadFactorConfiguration[]{new LoadFactorConfiguration()};

	public LoadFactorConfiguration[] getFactors() {
		return factors;
	}

	public void setFactors(LoadFactorConfiguration[] factors) {
		this.factors = factors;
	}
}
