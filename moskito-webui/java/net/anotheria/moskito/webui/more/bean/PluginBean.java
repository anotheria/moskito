package net.anotheria.moskito.webui.more.bean;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 23:39
 */
public class PluginBean {
	private String name;
	private String description;
	private String className;
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
