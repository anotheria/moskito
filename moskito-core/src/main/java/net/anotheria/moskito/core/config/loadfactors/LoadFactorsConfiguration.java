package net.anotheria.moskito.core.config.loadfactors;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * Configuration for load factors.
 *
 * @author lrosenberg
 * @since 22.07.20 16:04
 */
@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class LoadFactorsConfiguration implements Serializable {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * Configured factors.
	 */
	@Configure
	@SerializedName("@factors")
	private LoadFactorConfiguration[] factors = new LoadFactorConfiguration[0];

	public LoadFactorConfiguration[] getFactors() {
		return factors;
	}

	public void setFactors(LoadFactorConfiguration[] factors) {
		this.factors = factors;
	}
}
