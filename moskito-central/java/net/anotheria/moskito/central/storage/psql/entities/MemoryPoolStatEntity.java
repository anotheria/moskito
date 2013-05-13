package net.anotheria.moskito.central.storage.psql.entities;

import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author dagafonov
 *
 */
@Entity
@Table(name = "memorypoolstats")
@DiscriminatorValue("memorypool")
public class MemoryPoolStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String maximum;
	/**
	 * 
	 */
	private String maxUsed;
	/**
	 * 
	 */
	private String minUsed;
	/**
	 * 
	 */
	private String maxCommitted;
	/**
	 * 
	 */
	private String minCommitted;
	/**
	 * 
	 */
	private String init;
	/**
	 * 
	 */
	private String committed;
	/**
	 * 
	 */
	private String used;

	@Override
	public void setStats(Map<String, String> stats) {
		setMaximum(stats.get("MAX"));
		setMaxUsed(stats.get("MAX_USED"));
		setMaxCommitted(stats.get("MAX_COMMITED"));
		setInit(stats.get("INIT"));
		setCommitted(stats.get("COMMITED"));
		setMinCommitted(stats.get("MIN_COMMITED"));
		setMinUsed(stats.get("MIN_USED"));
		setUsed(stats.get("USED"));
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMaxUsed() {
		return maxUsed;
	}

	public void setMaxUsed(String maxUsed) {
		this.maxUsed = maxUsed;
	}

	public String getMinUsed() {
		return minUsed;
	}

	public void setMinUsed(String minUsed) {
		this.minUsed = minUsed;
	}

	public String getMaxCommitted() {
		return maxCommitted;
	}

	public void setMaxCommitted(String maxCommitted) {
		this.maxCommitted = maxCommitted;
	}

	public String getMinCommitted() {
		return minCommitted;
	}

	public void setMinCommitted(String minCommitted) {
		this.minCommitted = minCommitted;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
	}

	public String getCommitted() {
		return committed;
	}

	public void setCommitted(String committed) {
		this.committed = committed;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

}
