package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.config.plugins.PluginConfig;
import net.anotheria.moskito.core.plugins.PluginRepository;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.03.14 22:53
 */
public class AdditionalFunctionalityAPIImpl extends AbstractMoskitoAPIImpl implements AdditionalFunctionalityAPI{
	@Override
	public List<PluginAO> getPlugins() throws APIException {
		List<String> pluginNames = PluginRepository.getInstance().getPluginNames();
		ArrayList<PluginAO> ret = new ArrayList<PluginAO>();
		for (String s : pluginNames){
			PluginAO ao = new PluginAO();

			ao.setName(s);
			try{
				ao.setDescription(""+PluginRepository.getInstance().getPlugin(s));
			}catch(Exception e){
				ao.setDescription("Error: "+e.getMessage());
			}

			PluginConfig config = PluginRepository.getInstance().getConfig(s);
			if (config==null){
				ao.setClassName("-not found-");
				ao.setConfigurationName("-not found-");
			}else{
				ao.setConfigurationName(config.getConfigurationName());
				ao.setClassName(config.getClassName());
			}
			ret.add(ao);
		}
		return ret;

	}

	@Override
	public void removePlugin(String pluginName) throws APIException {
		PluginRepository.getInstance().removePlugin(pluginName);
	}

	@Override
	public void forceIntervalUpdate(String intervalName) throws APIException {
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
	}
}
