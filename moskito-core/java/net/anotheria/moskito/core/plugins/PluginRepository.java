package net.anotheria.moskito.core.plugins;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.plugins.PluginConfig;
import net.anotheria.moskito.core.config.plugins.PluginsConfig;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 15:47
 */
public class PluginRepository {

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(PluginRepository.class);

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

	private PluginRepository(){
		System.out.println("plugin repository loaded.");
	}

	private void setup(){
		PluginsConfig config = MoskitoConfigurationHolder.getConfiguration().getPluginsConfig();
		System.out.println("Loading configs");
		if (config.getPlugins()==null)
			return;
		for (PluginConfig pc : config.getPlugins()){
			log.info("Loading plugin "+pc);
			System.out.println("Loading config "+pc);
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

	public List<String> getPluginNames() {
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(plugins.keySet());
		return ret;
	}

	public MoskitoPlugin getPlugin(String name){
		return plugins.get(name);
	}

	public PluginConfig getConfig(String name){
		return configs.get(name);
	}

	private static class PluginRepositoryHolder{
		private static final PluginRepository instance = new PluginRepository();
		static{
			instance.setup();
		}
	}
}
