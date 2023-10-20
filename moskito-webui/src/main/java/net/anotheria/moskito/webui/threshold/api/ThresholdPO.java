package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.api.TieablePO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This parameter object is used to create a new threshold.
 *
 * @author lrosenberg
 * @since 12.02.13 16:00
 */
@XmlRootElement()
public class ThresholdPO extends TieablePO implements FormBean, Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1909814742598065614L;

	/**
	 * Direction for the yellow guard.
	 */
	private String yellowDir;
	/**
	 * Value for the yellow guard.
	 */
	private String yellowValue;

	/**
	 * Direction for the orange guard.
	 */
	private String orangeDir;
	/**
	 * Value for the orange guard.
	 */
	private String orangeValue;

	/**
	 * Direction for the red guard.
	 */
	private String redDir;
	/**
	 * Value for the red guard.
	 */
	private String redValue;

	/**
	 * Direction for the purple guard.
	 */
	private String purpleDir;
	/**
	 * Value for the purple guard.
	 */
	private String purpleValue;

	/**
	 * Direction for the green guard.
	 */
	private String greenDir;
	/**
	 * Value for the green guard.
	 */
	private String greenValue;


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
