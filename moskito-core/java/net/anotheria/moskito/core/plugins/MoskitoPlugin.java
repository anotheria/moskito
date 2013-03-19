package net.anotheria.moskito.core.plugins;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 15:39
 */
public interface MoskitoPlugin {
	/**
	 * Called by the plugin repository.
	 * @param configurationName
	 */
	void setConfigurationName(String configurationName);

	/**
	 * Called after the plugin has been loaded and allows the initialization of the plugin.
	 */
	void initialize();

	/**
	 * Called before the plugin should be unloaded. The plugin should cleanup it traces in this method.
	 */
	void deInitialize();
}
