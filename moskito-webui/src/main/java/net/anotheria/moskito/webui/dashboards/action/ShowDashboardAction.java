package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardWidget;
import net.anotheria.moskito.core.config.thresholds.GuardConfig;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardChartAO;
import net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean;
import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.gauges.bean.GaugeBean;
import net.anotheria.moskito.webui.producers.api.NullProducerAO;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.util.ProducerUtility;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;
import net.anotheria.moskito.webui.util.WebUIConfig;
import net.anotheria.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static net.anotheria.moskito.webui.threshold.util.ThresholdStatusBeanUtility.getThresholdBeans;

/**
 * This action renders a dashboard. If no dashboard is selected explicitly the first dashboard is taken.
 *
 * @author lrosenberg
 * @since 12.02.15 14:02
 */
public class ShowDashboardAction extends BaseDashboardAction {

	/**
	 * Default dashboard refresh rate in ms.
	 */
	private static final long DEFAULT_DASHBOARD_REFRESH_RATE = TimeUnit.SECONDS.toMillis(60);

	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dashboardName = getSelectedDashboard(request);
		Boolean gaugesPresent = false;
		Boolean chartsPresent = false;
		Boolean thresholdsPresent = false;
		Boolean producersPresent = false;

		Map<String, GraphDataBean> graphData = new HashMap<>();

		//set default values, allow to exit previously.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("producersPresent", producersPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent || producersPresent));


		if (dashboardName==null)
			dashboardName = getDashboardAPI().getDefaultDashboardName();
		if (dashboardName==null){
			//there are no configured dashboards
			return actionMapping.success();
		}

		DashboardConfig selectedDashboardConfig = getDashboardAPI().getDashboardConfig(dashboardName);
		if (selectedDashboardConfig == null) {
			throw new Exception("Dashboard '"+dashboardName+"' not found.");
		}
		request.setAttribute("selectedDashboard", dashboardName);

		DashboardAO dashboard = getDashboardAPI().getDashboard(dashboardName);
		List<ThresholdStatusBean> thresholdStatusBeans = getThresholdBeans(dashboard.getThresholds());
		List<GaugeBean> gaugeBeans = getGaugeBeans(dashboard.getGauges());
		List<DashboardChartBean> dashboardChartAOList = getChartBeans(dashboard.getCharts());
		List<ProducerDecoratorBean> decoratedProducers = getDecoratedProducerBeans(dashboard.getProducers(), request, graphData);

		// Getting configured dashboard widgets
		List<DashboardWidget> widgets = new LinkedList<>();
		for (String widgetName : selectedDashboardConfig.getWidgets()) {
			widgets.add(DashboardWidget.findWidgetByName(widgetName));
		}

		// Now we definitely have a selected dashboard.
		// Prepare thresholds
		if (dashboard.getThresholds()!=null && dashboard.getThresholds().size()>0){
			request.setAttribute("thresholds", thresholdStatusBeans);
			thresholdsPresent = true;
		} else {
			widgets.remove(DashboardWidget.THRESHOLDS);
		}

		// Prepare gauges
		if (dashboard.getGauges()!=null && dashboard.getGauges().size()>0){
			request.setAttribute("gauges", gaugeBeans);
			gaugesPresent = true;
		} else {
			widgets.remove(DashboardWidget.GAUGES);
		}

		// Prepare charts
		if (dashboardChartAOList != null && dashboardChartAOList.size() > 0){
			request.setAttribute("charts", dashboardChartAOList);

			Map<String, List<GuardConfig>> thresholds = new LinkedHashMap<>();
			for (DashboardChartAO dashboardChartAO : dashboard.getCharts()) {
				thresholds.put(dashboardChartAO.getCaption(), getTresholdConfig(dashboardChartAO));
			}

			request.setAttribute("thresholdsGraph", thresholds);
			request.setAttribute("thresholdGraphColors", WebUIConfig.getInstance().getThresholdGraphColors());

			chartsPresent = true;
		} else {
			widgets.remove(DashboardWidget.CHARTS);
		}

		// Prepare producers
		if (decoratedProducers != null && decoratedProducers.size() > 0) {
			request.setAttribute("graphDatas", graphData.values());
			request.setAttribute("decorators", decoratedProducers);
			producersPresent = true;
		} else {
			widgets.remove(DashboardWidget.PRODUCERS);
		}

		// Set widgets to be displayed on dashboard
		request.setAttribute("widgets", widgets);

		// Setting possible dashboard names where producer can be added
		request.setAttribute("dashboardNames", org.apache.commons.lang.StringUtils.join(getDashboardAPI().getDashboardNames(), ','));

		//maybe the value has changed.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("producersPresent", producersPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent || producersPresent));

		String infoMessage = getInfoMessage();
		if (!StringUtils.isEmpty(infoMessage)) {
			request.setAttribute("infoMessage", infoMessage);
		}

		request.setAttribute("dashboardRestApiUrl", getDashboardRestApiUrl(request, dashboardName));
		request.setAttribute("dashboardRefreshRate", getDashboardRefreshRate(selectedDashboardConfig));

		return actionMapping.success();
	}

	private List<GuardConfig> getTresholdConfig(DashboardChartAO dashboardChartAO) {
		List<GuardConfig> guardConfigs = new ArrayList<>();

		if (dashboardChartAO.getChart().getSingleGraphAOs().size() == 1) {
			guardConfigs.addAll(dashboardChartAO.getChart().getSingleGraphAOs().get(0).getThreshold());
		}

		return guardConfigs;
	}

	@Override
	protected String getPageName() {
		return "dashboard";
	}

	private List<ProducerDecoratorBean> getDecoratedProducerBeans(List<String> producerIds, HttpServletRequest request, Map<String, GraphDataBean> graphData) throws APIException {
		if (producerIds != null && producerIds.size() > 0) {
			List<ProducerAO> producerAOs = getProducerAPI().getProducers(producerIds, getCurrentInterval(request), getCurrentUnit(request).getUnit());
			List<ProducerAO> producersWithoutErrors = new LinkedList<>();
			for (ProducerAO producerAO : producerAOs){
				if (producerAO instanceof NullProducerAO){
					addInfoMessage(((NullProducerAO) producerAO).getMessage());
				}else{
					producersWithoutErrors.add(producerAO);
				}
			}
			if (producersWithoutErrors != null && producersWithoutErrors.size() > 0) {
				return ProducerUtility.getDecoratedProducers(request, producersWithoutErrors, graphData);
			}
		}

		return new ArrayList<>();
	}

	private List<GaugeBean> getGaugeBeans(List<GaugeAO> gaugeAOList) throws APIException {
		List<GaugeBean> ret = new ArrayList<>();
		if (gaugeAOList == null || gaugeAOList.size() == 0)
			return ret;

		for (GaugeAO gaugeAO : gaugeAOList) {
			String dashboardNames = getDashboardAPI().getDashboardNamesWhichDoNotIncludeThisGauge(gaugeAO.getName());
			ret.add(new GaugeBean(gaugeAO, dashboardNames));
		}

		return ret;
	}


	private List<DashboardChartBean> getChartBeans(List<DashboardChartAO> dashboardChartAOList) throws APIException {
		List<DashboardChartBean> ret = new ArrayList<>();
		if (dashboardChartAOList == null || dashboardChartAOList.size() == 0)
			return ret;

		for (DashboardChartAO dashboardChartAO : dashboardChartAOList) {
			String dashboardNames = getDashboardAPI().getDashboardNamesWhichDoNotIncludeThisChart(dashboardChartAO.getCaption());
			ret.add(new DashboardChartBean(dashboardChartAO, dashboardNames));
		}
		return ret;
	}

	/**
	 * Creates Dashboard REST API url for given dashboard.
	 * Used at UI.
	 *
	 * @param request       {@link HttpServletRequest}
	 * @param dashboardName dashboard name
	 * @return Dashboard REST API url, including context path
	 */
	private String getDashboardRestApiUrl(final HttpServletRequest request, final String dashboardName) {
		String contextPath = request.getContextPath();

		if (contextPath == null) {
			contextPath = "";
		}

		if (!contextPath.endsWith("/")) {
			contextPath += "/";
		}

		return contextPath + "moskito-inspect-rest/dashboards/" + dashboardName;
	}

	/**
	 * Returns dashboard refresh rate in ms.
	 * Used at UI for refreshing the dashboard.
	 *
	 * @param selectedDashboardConfig {@link DashboardConfig}
	 * @return dashboard refresh rate in ms
	 */
	private long getDashboardRefreshRate(final DashboardConfig selectedDashboardConfig) {
		if (selectedDashboardConfig == null) {
			return DEFAULT_DASHBOARD_REFRESH_RATE;
		}

		return TimeUnit.SECONDS.toMillis(selectedDashboardConfig.getRefresh());
	}
}
