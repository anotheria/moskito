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
@Table(name = "osstats")
@DiscriminatorValue("os")
public class OSStatEntity extends StatisticsEntity {

	/**
	 * 
	 */
	private String freemb;
	/**
	 * 
	 */
	private String totalmb;
	/**
	 * 
	 */
	private String free;
	/**
	 * 
	 */
	private String processors;
	/**
	 * 
	 */
	private String cputime;
	/**
	 * 
	 */
	private String openFiles;
	/**
	 * 
	 */
	private String maxOpenFiles;
	/**
	 * 
	 */
	private String total;
	/**
	 * 
	 */
	private String minOpenFiles;

	@Override
	public void setStats(Map<String, String> stats) {
		freemb = stats.get("FREE MB");
		totalmb = stats.get("TOTAL MB");
		free = stats.get("FREE");
		processors = stats.get("Processors");
		cputime = stats.get("CPU TIME");
		openFiles = stats.get("Open Files");
		maxOpenFiles = stats.get("Max Open Files");
		total = stats.get("TOTAL");
		minOpenFiles = stats.get("Min Open Files");
	}

	public String getFreemb() {
		return freemb;
	}

	public void setFreemb(String freemb) {
		this.freemb = freemb;
	}

	public String getTotalmb() {
		return totalmb;
	}

	public void setTotalmb(String totalmb) {
		this.totalmb = totalmb;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public String getProcessors() {
		return processors;
	}

	public void setProcessors(String processors) {
		this.processors = processors;
	}

	public String getCputime() {
		return cputime;
	}

	public void setCputime(String cputime) {
		this.cputime = cputime;
	}

	public String getOpenFiles() {
		return openFiles;
	}

	public void setOpenFiles(String openFiles) {
		this.openFiles = openFiles;
	}

	public String getMaxOpenFiles() {
		return maxOpenFiles;
	}

	public void setMaxOpenFiles(String maxOpenFiles) {
		this.maxOpenFiles = maxOpenFiles;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMinOpenFiles() {
		return minOpenFiles;
	}

	public void setMinOpenFiles(String minOpenFiles) {
		this.minOpenFiles = minOpenFiles;
	}

}
