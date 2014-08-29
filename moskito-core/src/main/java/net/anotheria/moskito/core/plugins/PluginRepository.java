package net.anotheria.moskito.core.plugins;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.plugins.PluginConfig;
import net.anotheria.moskito.core.config.plugins.PluginsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Manages and controls plugins at runtime.
 *
 * @author lrosenberg
 * @since 19.03.13 15:47
 */
public class PluginRepository {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(PluginRepository.class);

	/**
	 * Plugins.
	 */
	private ConcurrentMap<String, MoskitoPlugin> plugins = new ConcurrentHashMap<String, MoskitoPlugin>();

	/**
	 * Cached configs.
	 */
	private ConcurrentMap<String,PluginConfig> configs = new ConcurrentHashMap<String,PluginConfig>();


	/**
	 * Returns plugin repository singleton instance.
	 * @return
	 */
	public static final PluginRepository getInstance(){
		return PluginRepositoryHolder.instance;
	}

	/**
	 * Private constructor.
	 */
	private PluginRepository(){
	}

	private void setup(){
		PluginsConfig config = MoskitoConfigurationHolder.getConfiguration().getPluginsConfig();
		if (config.getPlugins()==null)
			return;
		for (PluginConfig pc : config.getPlugins()){
			log.info("Loading plugin "+pc);
			try {
				MoskitoPlugin plugin = MoskitoPlugin.class.cast(
						Class.forName(pc.getClassName()).newInstance()
				);
				plugin.setConfigurationName(pc.getConfigurationName());
				addPlugin(pc.getName(), plugin, pc);
			} catch (InstantiationException e) {
				log.warn("Couldn't initialize plugin "+pc, e);
			} catch (IllegalAccessException e) {
				log.warn("Couldn't initialize plugin " + pc, e);
			} catch (ClassNotFoundException e) {
				log.warn("Couldn't initialize plugin " + pc, e);
			}


		}
	}

	/**
	 * Adds a new loaded plugin.
	 * @param name name of the plugin for ui.
	 * @param plugin the plugin instance.
	 * @param config plugin config which was used to load the plugin.
	 */
	public void addPlugin(String name, MoskitoPlugin plugin, PluginConfig config){
		plugins.put(name, plugin);
		try{
			plugin.initialize();
			configs.put(name, config);
		}catch(Exception e){
			log.warn("couldn't initialize plugin "+name+" - "+plugin+", removing", e);
			plugins.remove(name);
		}
	}

	/**
	 * Removes a plugin. This call will call deInitialize on the plugin. The plugin is responsible to
	 * free all used resources and un-register itself from listening.
	 * @param name name of the plugin.
	 */
	public void removePlugin(String name){
		configs.remove(name);
		MoskitoPlugin plugin = plugins.remove(name);
		if (plugin==null){
			log.warn("Trying to remove not registered plugin "+name);
			return;
		}
		try{
			plugin.deInitialize();
		}catch(Exception e){
			log.warn("Couldn't de-initialize plugin "+name+" - "+plugin,e );
		}
	}

	/**
	 * Returns the names of the active plugins.
	 * @return
	 */
	public List<String> getPluginNames() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(plugins.keySet());
		return ret;
	}

	/**
	 * Returns loaded plugin by name.
	 * @param name
	 * @return
	 */
	public MoskitoPlugin getPlugin(String name){
		return plugins.get(name);
	}

	/**
	 * Returns pluginconfig for the loaded plugin.
	 * @param name
	 * @return
	 */
	public PluginConfig getConfig(String name){
		return configs.get(name);
	}

	/**
	 * Singletonhelper.
	 */
	private static class PluginRepositoryHolder{
		private static final PluginRepository instance = new PluginRepository();
		static{
			instance.setup();
		}
	}
}
