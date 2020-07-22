package net.anotheria.moskito.core.config.loadfactors;

import com.google.gson.annotations.SerializedName;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:04
 */
@ConfigureMe
public class LoadFactorConfiguration {
	@Configure
	private String name;

	@Configure
	private String metric;
	@Configure
	private String interval;

	@Configure
	@SerializedName("@left")
	private LoadFactorParticipantConfiguration left ;
	@Configure
	@SerializedName("@right")
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

	//testing method
	private static final LoadFactorParticipantConfiguration makeLeft(){
		LoadFactorParticipantConfiguration aLeft = new LoadFactorParticipantConfiguration();
		aLeft.setProducerName("OrderController");
		return aLeft;
	}

	//testing method
	private static final LoadFactorParticipantConfiguration makeRight(){
		LoadFactorParticipantConfiguration aRight = new LoadFactorParticipantConfiguration();
		aRight.setProducerName("");
		aRight.setCategory("service");
		return aRight;
	}

}
