package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.04.15 12:45
 */
@DistributeMe(agentsSupport=false, moskitoSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface DashboardAPI extends API, Service{
	String getDefaultDashboardName() throws APIException;

	DashboardConfig getDashboardConfig(String name) throws APIException;

	List<DashboardDefinitionAO> getDashboardDefinitions() throws APIException;

	DashboardAO getDashboard(String name) throws APIException;

	List<String> getDashboardNames() throws APIException;

	void createDashboard(String dashboardName) throws APIException;

	void removeDashboard(String dashboardName) throws APIException;

	void addGaugeToDashboard(String dashboardName, String gaugeName) throws APIException;

	void removeGaugeFromDashboard(String dashboardName, String gaugeName) throws APIException;

	void addThresholdToDashboard(String dashboardName, String thresholdName) throws APIException;

	void removeThresholdFromDashboard(String dashboardName, String thresholdName) throws APIException;

	void addChartToDashboard(String dashboardName, String[] accNames) throws APIException;

	void removeChartFromDashboard(String dashboardName, String[] accNames) throws APIException;

	void addProducerToDashboard(String dashboardName, String producerName) throws APIException;

	void removeProducerFromDashboard(String dashboardName, String producerName) throws APIException;
}
