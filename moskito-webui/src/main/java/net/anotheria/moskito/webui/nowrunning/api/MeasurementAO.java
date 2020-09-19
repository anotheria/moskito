package net.anotheria.moskito.webui.nowrunning.api;

import net.anotheria.util.NumberUtils;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.09.20 16:19
 */
public class MeasurementAO implements Serializable {

	private static final long serialVersionUID =1L;

	private long starttime;
	private long age;
	private String description;
	private long endtime;
	private long duration;

	public long getStarttime() {
		return starttime;
	}

	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTimestamp(){
		return NumberUtils.makeISO8601TimestampString(getStarttime());
	}

	public String getEndTimestamp(){
		return NumberUtils.makeISO8601TimestampString(getEndtime());
	}
	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}
}
