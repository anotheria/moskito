package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * This class gathers all functionality that has no api of itself, but should have one for distribution concerns.
 *
 * @author lrosenberg
 * @since 24.03.14 22:52
 */
@DistributeMe(agentsSupport = false)
@SupportService
public interface AdditionalFunctionalityAPI extends API, Service{
	List<PluginAO> getPlugins() throws APIException;

	void removePlugin(String pluginName) throws APIException;

}
