package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * This class gathers all functionality that has no api of itself, but should have one for distribution concerns.
 *
 * @author lrosenberg
 * @since 24.03.14 22:52
 */
@DistributeMe(agentsSupport = false, moskitoSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
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

	/**
	 * Returns MoSKito Configuration
	 * @return
	 * @throws APIException
	 */
	MoskitoConfiguration getConfiguration() throws APIException;

	/**
	 * Returns all active error catchers.
	 * @return
	 * @throws APIException
	 */
	List<ErrorCatcherAO> getActiveErrorCatchers() throws APIException;

	/**
	 * Returns list of errors caught for this exception name.
	 * @param catcherName which is the name of the catcher, which can be well exception name, i.e. java.lang.NullPointerException.
	 * @param catcherType which is one of supported types. ErrorCatcherBean.CatcherType for details.
	 * @return list of  CaughtErrorAO objects.
	 * @throws APIException
	 */
	List<CaughtErrorAO> getCaughtErrorsByExceptionName(String catcherName, String catcherType) throws APIException;

}
