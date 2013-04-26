package net.anotheria.moskito.central.storage.psql;

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
@Table(name = "threadcountstats")
@DiscriminatorValue("threadcount")
public class ThreadCountStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String started;
	/**
	 * 
	 */
	private String daemon;
	/**
	 * 
	 */
	private String cur;
	/**
	 * 
	 */
	private String maximum;
	/**
	 * 
	 */
	private String minimum;

	@Override
	public void setStats(Map<String, String> stats) {
		started = stats.get("Started");
		daemon = stats.get("Daemon");
		cur = stats.get("Cur");
		maximum = stats.get("Max");
		minimum = stats.get("Min");
	}

	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	public String getDaemon() {
		return daemon;
	}

	public void setDaemon(String daemon) {
		this.daemon = daemon;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

}
