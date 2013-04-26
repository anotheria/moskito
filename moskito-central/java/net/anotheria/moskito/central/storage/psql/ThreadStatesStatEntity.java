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
@Table(name = "threadstatesstats")
@DiscriminatorValue("threadstates")
public class ThreadStatesStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String maximum;
	/**
	 * 
	 */
	private String minimum;
	/**
	 * 
	 */
	private String cur;

	@Override
	public void setStats(Map<String, String> stats) {
		maximum = stats.get("MAX");
		minimum = stats.get("MIN");
		cur = stats.get("CUR");
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

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

}
