package net.anotheria.moskito.webui.producers.api;

import net.anotheria.moskito.core.producers.IStats;

import java.io.Serializable;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.14 14:03
 */
public class ProducerAO implements Serializable{
	private String producerId;

	private List<IStats> stats;
	private String category;
	private String subsystem;
	private String producerClassName;

	private boolean inspectable;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public List<IStats> getStats() {
		return stats;
	}

	public void setStats(List<IStats> stats) {
		this.stats = stats;
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

	public String getProducerClassName() {
		return producerClassName;
	}

	public void setProducerClassName(String producerClassName) {
		this.producerClassName = producerClassName;
	}

	public boolean isInspectable() {
		return inspectable;
	}

	public void setInspectable(boolean inspectable) {
		this.inspectable = inspectable;
	}

}
