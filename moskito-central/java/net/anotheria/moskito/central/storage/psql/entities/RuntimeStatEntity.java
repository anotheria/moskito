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
@Table(name = "runtimestats")
@DiscriminatorValue("runtime")
public class RuntimeStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String uptime;
	/**
	 * 
	 */
	private String process;
	/**
	 * 
	 */
	private String starttime;

	@Override
	public void setStats(Map<String, String> stats) {
		uptime = stats.get("Uptime");
		process = stats.get("Process");
		starttime = stats.get("Starttime");
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

}
