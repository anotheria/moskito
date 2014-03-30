package net.anotheria.moskito.webui.shared.api;

import java.io.Serializable;

/**
 * Represents a plugin.
 *
 * @author lrosenberg
 * @since 19.03.13 23:39
 */
public class PluginAO implements Serializable{

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the plugin.
	 */
	private String name;
	/**
	 * Description of the plugin.
	 */
	private String description;
	/**
	 * Plugin class name.
	 */
	private String className;
	/**
	 * Name of the plugin configuration.
	 */
	private String configurationName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}
}
