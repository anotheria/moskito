package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Contains runtime information about a threshold.
 * @author lrosenberg
 *
 */
@XmlRootElement(name="ThresholdStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThresholdStatusAO implements IComparable, Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1974568139755756775L;

	/**
	 * Name of the threshold.
	 */
	@XmlElement
	private String name;
	/**
	 * Current status.
	 */
	@XmlTransient
	private String status;
	/**
	 * Current color.
	 */
	@XmlElement
	private String colorCode;
	/**
	 * Last change timestamp.
	 */
	@XmlElement
	private String timestamp;
	/**
	 * Threshold description.
	 */
	@XmlElement
	private String description;
	/**
	 * Current value.
	 */
	@XmlElement
	private String value;

	/**
	 * Previous color code.
	 */
	@XmlTransient
	private String previousColorCode;

	/**
	 * Previous status.
	 */
	@XmlTransient
	private String previousStatus;

	/**
	 * Status for sorting.
	 */
	@XmlTransient
	private ThresholdStatus statusForSorting;
	/**
	 * Timestamp for sorting.
	 */
	@XmlElement (name = "timestampInMillis")
	private long timestampForSorting;
	/**
	 * Id of the threshold.
	 */
	@XmlElement
	private String id;

	@XmlElement
	private long flipCount;
	private boolean custom;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override public String toString(){
		return getName()+ ' ' +getStatus()+ ' ' +getTimestamp()+ ' ' +getDescription()+ ' ' +getValue();
	}
	@Override
	public int compareTo(IComparable anotherObject, int method) {
		ThresholdStatusAO anotherBean = (ThresholdStatusAO)anotherObject;
		switch(method){
		case ThresholdStatusAOSortType.BY_CHANGE:
			return BasicComparable.compareLong(timestampForSorting, anotherBean.timestampForSorting);
		case ThresholdStatusAOSortType.BY_NAME:
			return BasicComparable.compareString(name, anotherBean.name);
		case ThresholdStatusAOSortType.BY_STATUS:
			return statusForSorting.compareTo(anotherBean.statusForSorting);
		}
		throw new IllegalArgumentException("Unknow sort method: "+method);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ThresholdStatusAO that = (ThresholdStatusAO) o;

		return name.equals(that.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public ThresholdStatus getStatusForSorting() {
		return statusForSorting;
	}
	public void setStatusForSorting(ThresholdStatus statusForSorting) {
		this.statusForSorting = statusForSorting;
	}
	public long getTimestampForSorting() {
		return timestampForSorting;
	}
	public void setTimestampForSorting(long timestampForSorting) {
		this.timestampForSorting = timestampForSorting;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public long getFlipCount() {
		return flipCount;
	}

	public void setFlipCount(long flipCount) {
		this.flipCount = flipCount;
	}

	public String getPreviousColorCode() {
		return previousColorCode;
	}

	public void setPreviousColorCode(String previousColorCode) {
		this.previousColorCode = previousColorCode;
	}

	public String getPreviousStatus() {
		return previousStatus;
	}

	public void setPreviousStatus(String previousStatus) {
		this.previousStatus = previousStatus;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public boolean getCustom() {
		return custom;
	}
}
