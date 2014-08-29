package net.anotheria.moskito.webui.threshold.resource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.02.13 16:10
 */
@XmlRootElement
public class StatusForm {
	public List<String> getThresholdNames() {
		return thresholdNames;
	}

	public void setThresholdNames(List<String> thresholdNames) {
		this.thresholdNames = thresholdNames;
	}

	@XmlElement
	private List<String> thresholdNames;
}
