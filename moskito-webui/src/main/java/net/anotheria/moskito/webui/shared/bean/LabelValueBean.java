package net.anotheria.moskito.webui.shared.bean;

/**
 * Value for checkbox.
 *
 * @author lrosenberg
 * @since 24.03.14 00:08
 */
public class LabelValueBean {
	/**
	 * Label of the option.
	 */
	private String label;
	/**
	 * Value of the option.
	 */
	private String value;

	public LabelValueBean() {

	}

	public LabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override public String toString(){
        return label + ' ' + value;
	}
}
