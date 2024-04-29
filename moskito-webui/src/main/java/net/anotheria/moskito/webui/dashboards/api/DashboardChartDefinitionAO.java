package net.anotheria.moskito.webui.dashboards.api;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A chart definition.
 *
 * @author lrosenberg
 * @since 15.04.15 22:41
 */
@XmlRootElement(name="chart")
@XmlAccessorType(XmlAccessType.FIELD)
public class DashboardChartDefinitionAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -9094377030947294973L;

	/**
	 * Chart box caption. Optional but recommended, especially if you have more than one accumulator.
	 */
	@XmlElement
	private String caption;

	/**
	 * Names of accumulators to build chart from.
	 */
	@XmlElement
	private List<String> accumulatorNames = Collections.emptyList();

	public List<String> getAccumulatorNames() {
		return accumulatorNames;
	}

	public void setAccumulatorNames(List<String> accumulatorNames) {
		this.accumulatorNames = accumulatorNames;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
}
