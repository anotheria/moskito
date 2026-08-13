package net.anotheria.moskito.core.config.producers;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration of the top-producers ranking. The ranking is maintained by
 * {@link net.anotheria.moskito.core.topproducers.TopProducersRepository} which continuously ranks all producers by a
 * set of categories (requests, time, errors, ...) so the heaviest producers can be presented as optimization targets.
 *
 * @author lrosenberg
 */
@ConfigureMe(allfields = true)
public class TopProducersConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * If true, the top-producers ranking is started automatically at moskito startup, so the ranking already contains
	 * meaningful data by the time it is first queried. If false, the ranking only starts once it is first accessed.
	 */
	@Configure
	private boolean enabled = true;

	/**
	 * Name of the interval the ranking is updated on.
	 */
	@Configure
	private String intervalName = "1m";

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	@Override
	public String toString() {
		return "enabled: " + enabled + ", intervalName: " + intervalName;
	}
}
