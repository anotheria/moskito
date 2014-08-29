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

	/**
	 * Forces an intentional interval update. This can be useful to make a snapshot between two intervals, which are
	 * not time related (for example the snapshot interval) or to force a time interval update for testing purposes.
	 * @param intervalName
	 * @throws APIException
	 */
	void forceIntervalUpdate(String intervalName) throws APIException;

	/**
	 * Returns the list of available beans.
	 * @return
	 * @throws APIException
	 */
	List<MBeanWrapperAO> getMBeans() throws APIException;

	/**
	 * Returns interval infos.
	 * @return
	 * @throws APIException
	 */
	List<IntervalInfoAO> getIntervalInfos() throws APIException;

	/**
	 * Returns current configuration as string for presentation purposes.
	 * @return
	 * @throws APIException
	 */
	String getConfigurationAsString() throws APIException;

	Long getIntervalUpdateTimestamp(String intervalName) throws APIException;
}
