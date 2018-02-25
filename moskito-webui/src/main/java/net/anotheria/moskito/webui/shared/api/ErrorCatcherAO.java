package net.anotheria.moskito.webui.shared.api;

import java.io.Serializable;

/**
 * Used to transfer ErrorCatcherObject to UI.
 *
 * @author lrosenberg
 * @since 05.06.17 23:47
 */
public class ErrorCatcherAO implements Serializable{

	private static long serialVersionUID = 1;

	/**
	 * Name of the catcher, usually name of the exception.
	 */
	private String name;
	/**
	 * Count of caught errors if any. This is specific to the catcher.
	 */
	private int count;
	/**
	 * Type of the catcher, one of DEFAULT, CUSTOMER or EXCEPTION-BOUND.
	 */
	private String type;
	/**
	 * If true, there are errors to be expected.
	 */
	private boolean inspectable;
	/**
	 * Target of the catcher (log, memory, custom).
	 */
	private String target;

	private String configurationParameter;

	public String getConfigurationParameter() {
		return configurationParameter;
	}

	public void setConfigurationParameter(String configurationParameter) {
		this.configurationParameter = configurationParameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public boolean isInspectable() {
		return inspectable;
	}

	public void setInspectable(boolean inspectable) {
		this.inspectable = inspectable;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "ErrorCatcherAO{" +
				"name='" + name + '\'' +
				", count=" + count +
				", type='" + type + '\'' +
				", inspectable=" + inspectable +
				", target='" + target + '\'' +
				'}';
	}
}
