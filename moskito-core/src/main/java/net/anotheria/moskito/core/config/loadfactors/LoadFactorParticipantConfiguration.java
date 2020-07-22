package net.anotheria.moskito.core.config.loadfactors;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.07.20 16:15
 */
@ConfigureMe
public class LoadFactorParticipantConfiguration {
	@Configure
	private String producerName;
	@Configure
	private String category;
	@Configure
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
