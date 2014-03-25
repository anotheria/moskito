package net.anotheria.moskito.webui.producers.api;

import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.core.producers.IStats;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.14 14:03
 */
public class ProducerAO implements Serializable{
	private String producerId;

	private String category;
	private String subsystem;
	private String producerClassName;

	private List<StatValueAO> firstStatsValues;

	/**
	 * CreationInfo is set for all inspectable producers.
	 */
	private CreationInfo creationInfo;

	/**
	 * All stat values as list of lists. The included list contains one stat - line with multiple values.
	 */
	private List<StatLineAO> lines;

	private Class<? extends IStats> statsClazz;

	public ProducerAO(){
		lines = new LinkedList<StatLineAO>();
	}

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
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

	/**
	 * Is inspectable, true if there is a creation info available.
	 * @return
	 */
	public boolean isInspectable() {
		return creationInfo!=null;
	}

	public Class<? extends IStats> getStatsClazz() {
		return statsClazz;
	}

	public void setStatsClazz(Class<? extends IStats> statsClazz) {
		this.statsClazz = statsClazz;
	}


	public List<StatValueAO> getFirstStatsValues() {
		return firstStatsValues;
	}

	public void setFirstStatsValues(List<StatValueAO> firstStatsValues) {
		this.firstStatsValues = firstStatsValues;
	}

	public void addStatLine(StatLineAO line){
		lines.add(line);
	}
	public List<StatLineAO> getLines() {
		return lines;
	}
	public CreationInfo getCreationInfo() {
		return creationInfo;
	}

	public void setCreationInfo(CreationInfo creationInfo) {
		this.creationInfo = creationInfo;
	}




}
