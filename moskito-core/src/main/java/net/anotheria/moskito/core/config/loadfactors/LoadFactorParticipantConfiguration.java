package net.anotheria.moskito.core.config.loadfactors;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * One of load factors participants.
 *
 * @author lrosenberg
 * @since 22.07.20 16:15
 */
@ConfigureMe
public class LoadFactorParticipantConfiguration implements Serializable {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * Name of producer for this participant.
	 */
	@Configure
	private String producerName;
	/**
	 * Name of the category for this LF participant. All producers from one category will be considered.
	 */
	@Configure
	private String category;
	@Configure
	/**
	 * Name of the subsystem for this LF participant. All producers from one subsystem will be considered.
	 */
	private String subsystem;

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String describe(){
		return valueOrEmpty(category)+"/"+valueOrEmpty(subsystem)+"/"+valueOrEmpty(producerName);
	}

	private String valueOrEmpty(String v){
		return v == null || v.length() ==0 ?
				" - " : " "+v+" ";
	}
}
