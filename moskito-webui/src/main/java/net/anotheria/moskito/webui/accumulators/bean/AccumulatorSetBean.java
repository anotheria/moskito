package net.anotheria.moskito.webui.accumulators.bean;

/**
 * Representation of an accumulator set in frontend.
 *
 * @author lrosenberg
 * @since 14.01.15 10:20
 */
public class AccumulatorSetBean {
	private String name;
	private String accumulatorNames;
	private String link;

	public String getAccumulatorNames() {
		return accumulatorNames;
	}

	public void setAccumulatorNames(String accumulatorNames) {
		this.accumulatorNames = accumulatorNames;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
