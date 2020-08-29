package net.anotheria.moskito.core.config.loadfactors;

import com.google.gson.annotations.SerializedName;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * A single load factor configuration.
 *
 * @author lrosenberg
 * @since 22.07.20 16:04
 */
@ConfigureMe
public class LoadFactorConfiguration implements Serializable {
	/**
	 * Name of the load factor.
	 */
	@Configure
	private String name;

	/**
	 * Metric or statistic that the load factor is built upon, for example request count or time or errors.
	 */
	@Configure
	private String metric;
	/**
	 * Interval the metric relies on.
	 */
	@Configure
	private String interval;

	@Configure
	@SerializedName("@left")
	/**
	 * The left side participants.
	 */
	private LoadFactorParticipantConfiguration left ;
	@Configure
	@SerializedName("@right")
	/**
	 * The right side participants.
	 */
	private LoadFactorParticipantConfiguration right ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public LoadFactorParticipantConfiguration getLeft() {
		return left;
	}

	public void setLeft(LoadFactorParticipantConfiguration left) {
		this.left = left;
	}

	public LoadFactorParticipantConfiguration getRight() {
		return right;
	}

	public void setRight(LoadFactorParticipantConfiguration right) {
		this.right = right;
	}
}
