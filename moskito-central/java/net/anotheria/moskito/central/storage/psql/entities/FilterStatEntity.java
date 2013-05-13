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
@Table(name = "filterstats")
@DiscriminatorValue("filter")
public class FilterStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String last;
	/**
	 * 
	 */
	private String maximum;
	/**
	 * 
	 */
	private String cr;
	/**
	 * 
	 */
	private String err;
	/**
	 * 
	 */
	private String mcr;
	/**
	 * 
	 */
	private String average;
	/**
	 * 
	 */
	private String tt;
	/**
	 * 
	 */
	private String tr;
	/**
	 * 
	 */
	private String minimum;

	@Override
	public void setStats(Map<String, String> stats) {
		last = stats.get("Last");
		maximum = stats.get("Max");
		maximum = stats.get("CR");
		err = stats.get("ERR");
		mcr = stats.get("MCR");
		average = stats.get("Avg");
		tt = stats.get("TT");
		tr = stats.get("TR");
		minimum = stats.get("Min");
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getCr() {
		return cr;
	}

	public void setCr(String cr) {
		this.cr = cr;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	public String getMcr() {
		return mcr;
	}

	public void setMcr(String mcr) {
		this.mcr = mcr;
	}

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getTr() {
		return tr;
	}

	public void setTr(String tr) {
		this.tr = tr;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	@Override
	public String toString() {
		return "FilterStatEntity [last=" + last + ", maximum=" + maximum + ", cr=" + cr + ", err=" + err + ", mcr=" + mcr + ", average=" + average
				+ ", tt=" + tt + ", tr=" + tr + ", minimum=" + minimum + "]";
	}

}
