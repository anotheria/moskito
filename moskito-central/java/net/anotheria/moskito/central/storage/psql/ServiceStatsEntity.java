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
@Table(name = "servicestats")
@DiscriminatorValue("service")
public class ServiceStatsEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String last;
	/**
	 * 
	 */
	private String cr;
	/**
	 * 
	 */
	private String maximum;
	/**
	 * 
	 */
	private String mcr;
	/**
	 * 
	 */
	private String err;
	/**
	 * 
	 */
	private String tr;
	/**
	 * 
	 */
	private String tt;
	/**
	 * 
	 */
	private String average;
	/**
	 * 
	 */
	private String minimum;

	@Override
	public void setStats(Map<String, String> stats) {
		this.last = stats.get("Last");
		this.cr = stats.get("CR");
		this.maximum = stats.get("Max");
		this.mcr = stats.get("MCR");
		this.err = stats.get("ERR");
		this.tr = stats.get("TR");
		this.tt = stats.get("TT");
		this.average = stats.get("Avg");
		this.minimum = stats.get("Min");
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMcr() {
		return mcr;
	}

	public void setMcr(String mcr) {
		this.mcr = mcr;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	@Override
	public String toString() {
		return "TestStatEntity [last=" + last + ", cr=" + cr + ", maximum=" + maximum + ", mcr=" + mcr + ", err=" + err + ", tr=" + tr + ", tt=" + tt
				+ ", average=" + average + ", minimum=" + minimum + "]";
	}

}
