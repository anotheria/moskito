package net.anotheria.moskito.webui.threshold.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Represents a single threshold change alert object.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ThresholdAlertAO implements Serializable{
	/**
	 * Id of the threshold.
	 */
	private String id;
	/**
	 * Name of the threshold.
	 */
	private String name;
	/*
	 * Timestamp of the alert.
	 */
	private String timestamp;
	/**
	 * Status before alert was triggered.
	 */
	private String oldStatus;
	/**
	 * Color before alert was triggered.
	 */
	private String oldColorCode;
	/**
	 * Value before alert was triggered.
	 */
	private String oldValue;

	/**
	 * Status after alert was triggered.
	 */
	private String newStatus;
	/**
	 * Color after the alert was triggered.
	 */
	private String newColorCode;
	/**
	 * New value, after the alert.
	 */
	private String newValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public String getOldColorCode() {
		return oldColorCode;
	}
	public void setOldColorCode(String oldColorCode) {
		this.oldColorCode = oldColorCode;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	public String getNewColorCode() {
		return newColorCode;
	}
	public void setNewColorCode(String newColorCode) {
		this.newColorCode = newColorCode;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
