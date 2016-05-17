package net.anotheria.moskito.webui.plugins;

import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskito.core.plugins.MoskitoPlugin;
import net.anotheria.moskito.core.plugins.PluginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This configurator is used to add additional action mappings for plugins. It only is called at startup (limitations of
 * the ano-maf framework, therefor a plugin should be presented at runtime).
 *
 * @author lrosenberg
 * @since 16.05.16 23:56
 */
public class PluginMappingsConfigurator implements ActionMappingsConfigurator {

	private static Logger log = LoggerFactory.getLogger(PluginMappingsConfigurator.class);

	@Override
	public void configureActionMappings(ActionMappings actionMappings) {
		List<MoskitoPlugin> plugins = PluginRepository.getInstance().getPlugins();
		for (MoskitoPlugin plugin : plugins){
			if (plugin instanceof VisualMoSKitoPlugin){
				log.debug("Adding mappings for "+plugin);
				((VisualMoSKitoPlugin)plugin).addBindings(actionMappings);
			}
		}
	}
}
