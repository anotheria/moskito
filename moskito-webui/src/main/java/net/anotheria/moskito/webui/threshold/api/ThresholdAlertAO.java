package net.anotheria.moskito.webui.threshold.api;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Represents a single threshold change alert object.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ThresholdAlert")
public class ThresholdAlertAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1;

	/**
	 * Id of the threshold.
	 */
    @XmlElement
	private String id;
	/**
	 * Name of the threshold.
	 */
    @XmlElement
	private String name;
	/*
	 * Timestamp of the alert.
	 */
    @XmlElement
	private String timestamp;
	/**
	 * Status before alert was triggered.
	 */
    @XmlElement
	private String oldStatus;
	/**
	 * Color before alert was triggered.
	 */
    @XmlElement
	private String oldColorCode;
	/**
	 * Value before alert was triggered.
	 */
    @XmlElement
	private String oldValue;

	/**
	 * Status after alert was triggered.
	 */
    @XmlElement
	private String newStatus;
	/**
	 * Color after the alert was triggered.
	 */
    @XmlElement
	private String newColorCode;
	/**
	 * New value, after the alert.
	 */
    @XmlElement
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
