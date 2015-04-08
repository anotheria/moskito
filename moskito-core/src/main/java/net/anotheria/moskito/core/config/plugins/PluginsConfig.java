package net.anotheria.moskito.core.config.plugins;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Config for plugins.
 *
 * @author lrosenberg
 * @since 19.03.13 15:48
 */
@ConfigureMe
public class PluginsConfig implements Serializable {
	/**
	 * Configured plugin list.
	 */
	@Configure private PluginConfig[] plugins;

	public PluginConfig[] getPlugins() {
		return plugins;
	}

	public void setPlugins(PluginConfig[] plugins) {
		this.plugins = plugins;
	}
}
