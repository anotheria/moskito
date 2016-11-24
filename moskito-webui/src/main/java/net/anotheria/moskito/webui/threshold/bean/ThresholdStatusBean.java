package net.anotheria.moskito.webui.threshold.bean;

import net.anotheria.moskito.core.threshold.ThresholdStatus;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

/**
 * TODO comment this class
 *
 */
public class ThresholdStatusBean  {
	 /**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1974568139755756775L;

	/**
	 * Name of the threshold.
	 */
	private String name;
	/**
	 * Current status.
	 */
	private String status;
	/**
	 * Current color.
	 */
	private String colorCode;
	/**
	 * Last change timestamp.
	 */
	private String timestamp;
	/**
	 * Threshold description.
	 */
	private String description;
	/**
	 * Current value.
	 */
	private String value;

	/**
	 * Previous color code.
	 */
	private String previousColorCode;

	/**
	 * Previous status.
	 */
	private String previousStatus;

	/**
	 * Status for sorting.
	 */
	private ThresholdStatus statusForSorting;
	/**
	 * Timestamp for sorting.
	 */
	private long timestampForSorting;
	/**
	 * Id of the threshold.
	 */
	private String id;
	/**
	 *
	 */
	private long flipCount;
	/**
	 * Dashboards where this gauge can be added.
	 */
	private String dashboardsToAdd;

	public ThresholdStatusBean(ThresholdStatusAO thresholdStatusAO, String dashboardsToAdd) {
		this.name = thresholdStatusAO.getName();
		this.status = thresholdStatusAO.getStatus();
		this.colorCode = thresholdStatusAO.getColorCode();
		this.timestamp = thresholdStatusAO.getTimestamp();
		this.description = thresholdStatusAO.getDescription();
		this.value = thresholdStatusAO.getValue();
		this.previousColorCode = thresholdStatusAO.getPreviousColorCode();
		this.previousStatus = thresholdStatusAO.getPreviousStatus();
		this.statusForSorting = thresholdStatusAO.getStatusForSorting();
		this.timestampForSorting = thresholdStatusAO.getTimestampForSorting();
		this.id = thresholdStatusAO.getId();
		this.flipCount = thresholdStatusAO.getFlipCount();
		this.dashboardsToAdd = dashboardsToAdd;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getDashboardsToAdd() {
		return dashboardsToAdd;
	}

	public void setDashboardsToAdd(String dashboardsToAdd) {
		this.dashboardsToAdd = dashboardsToAdd;
	}

	@Override
	public String toString() {
		return "ThresholdStatusBean{" +
				"name='" + name + '\'' +
				", status='" + status + '\'' +
				", colorCode='" + colorCode + '\'' +
				", timestamp='" + timestamp + '\'' +
				", description='" + description + '\'' +
				", value='" + value + '\'' +
				", previousColorCode='" + previousColorCode + '\'' +
				", previousStatus='" + previousStatus + '\'' +
				", statusForSorting=" + statusForSorting +
				", timestampForSorting=" + timestampForSorting +
				", id='" + id + '\'' +
				", flipCount=" + flipCount +
				", dashboardsToAdd='" + dashboardsToAdd + '\'' +
				'}';
	}
}








