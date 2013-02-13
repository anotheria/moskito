package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.maf.bean.FormBean;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.13 16:00
 */
@XmlRootElement
public class ThresholdPO implements FormBean{
	private String producerId;
	private String valueName;
	private String unit;
	private String name;
	private String interval;
	private String statName;

	private String yellowDir;
	private String yellowValue;

	private String orangeDir;
	private String orangeValue;

	private String redDir;
	private String redValue;

	private String purpleDir;
	private String purpleValue;

	private String greenDir;
	private String greenValue;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getYellowDir() {
		return yellowDir;
	}

	public void setYellowDir(String yellowDir) {
		this.yellowDir = yellowDir;
	}

	public String getYellowValue() {
		return yellowValue;
	}

	public void setYellowValue(String yellowValue) {
		this.yellowValue = yellowValue;
	}

	public String getOrangeDir() {
		return orangeDir;
	}

	public void setOrangeDir(String orangeDir) {
		this.orangeDir = orangeDir;
	}

	public String getOrangeValue() {
		return orangeValue;
	}

	public void setOrangeValue(String orangeValue) {
		this.orangeValue = orangeValue;
	}

	public String getRedDir() {
		return redDir;
	}

	public void setRedDir(String redDir) {
		this.redDir = redDir;
	}

	public String getRedValue() {
		return redValue;
	}

	public void setRedValue(String redValue) {
		this.redValue = redValue;
	}

	public String getPurpleDir() {
		return purpleDir;
	}

	public void setPurpleDir(String purpleDir) {
		this.purpleDir = purpleDir;
	}

	public String getPurpleValue() {
		return purpleValue;
	}

	public void setPurpleValue(String purpleValue) {
		this.purpleValue = purpleValue;
	}

	public String getGreenDir() {
		return greenDir;
	}

	public void setGreenDir(String greenDir) {
		this.greenDir = greenDir;
	}

	public String getGreenValue() {
		return greenValue;
	}

	public void setGreenValue(String greenValue) {
		this.greenValue = greenValue;
	}
}
