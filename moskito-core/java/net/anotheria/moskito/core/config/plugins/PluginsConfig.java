package net.anotheria.moskito.core.config.plugins;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 15:48
 */
@ConfigureMe
public class PluginsConfig {
	@Configure private PluginConfig[] plugins;

	public PluginConfig[] getPlugins() {
		return plugins;
	}

	public void setPlugins(PluginConfig[] plugins) {
		this.plugins = plugins;
	}
}
