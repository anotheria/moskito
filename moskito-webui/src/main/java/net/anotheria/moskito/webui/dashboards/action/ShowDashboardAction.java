package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardChartAO;
import net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean;
import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.gauges.bean.GaugeBean;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;
import net.anotheria.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static net.anotheria.moskito.webui.threshold.util.ThresholdStatusBeanUtility.getThresholdBeans;

/**
 * This action renders a dashboard. If no dashboard is selected explicitly the first dashboard is taken.
 *
 * @author lrosenberg
 * @since 12.02.15 14:02
 */
public class ShowDashboardAction extends BaseDashboardAction {

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String dashboardName = getSelectedDashboard(request);
		Boolean gaugesPresent = false;
		Boolean chartsPresent = false;
		Boolean thresholdsPresent = false;

		//set default values, allow to exit previously.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent));

		DashboardConfig selectedDashboard = getDashboardAPI().getDashboardConfig(dashboardName);
		if (dashboardName == null || selectedDashboard == null) {
			dashboardName = getDashboardAPI().getDefaultDashboardName();
			if (dashboardName == null) { // no dashboards present
				return actionMapping.success();
			}
			request.setAttribute("selectedDashboard", dashboardName);
		}

		DashboardAO dashboard = getDashboardAPI().getDashboard(dashboardName);
		List<ThresholdStatusBean> thresholdStatusBeans = getThresholdBeans(dashboard.getThresholds());
		List<GaugeBean> gaugeBeans = getGaugeBeans(dashboard.getGauges());
		List<DashboardChartBean> dashboardChartAOList = getChartBeans(dashboard.getCharts());

		//now we definitely have a selected dashboard.
		//prepare thresholds
		if (dashboard.getThresholds()!=null && dashboard.getThresholds().size()>0){
			request.setAttribute("thresholds", thresholdStatusBeans);
			thresholdsPresent = true;
		}

		//prepare gauges
		if (dashboard.getGauges()!=null && dashboard.getGauges().size()>0){
			request.setAttribute("gauges", gaugeBeans);
			gaugesPresent = true;
		}

		//prepare charts
		if (dashboardChartAOList!=null && dashboardChartAOList.size()>0){
			request.setAttribute("charts", dashboardChartAOList);
			chartsPresent = true;
		}

		//maybe the value has changed.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent));

		String infoMessage = getInfoMessage();
		if (!StringUtils.isEmpty(infoMessage)) {
			request.setAttribute("infoMessage", infoMessage);
		}

		return actionMapping.success();
	}

	@Override
	protected String getPageName() {
		return "dashboard";
	}

	private List<GaugeBean> getGaugeBeans(List<GaugeAO> gaugeAOList) throws APIException {
		List<GaugeBean> ret = new ArrayList<>();
		if (gaugeAOList == null || gaugeAOList.size() == 0)
			return ret;

		List<DashboardAO> dashboardAOList = new ArrayList<>();
		for(String name : getDashboardAPI().getDashboardNames()) {
			dashboardAOList.add(getDashboardAPI().getDashboard(name));
		}
		for (GaugeAO gaugeAO : gaugeAOList) {
			String dashboardNames = "";
			for(DashboardAO dashboardAO: dashboardAOList) {
				if (dashboardAO.getGauges() == null || !dashboardAO.getGauges().contains(gaugeAO)) {
					dashboardNames += dashboardAO.getName()+",";
				}
			}
			if (dashboardNames.length() > 0)
				dashboardNames = dashboardNames.substring(0, dashboardNames.length()-1);
			ret.add(new GaugeBean(gaugeAO, dashboardNames));
		}

		return ret;
	}


	private List<DashboardChartBean> getChartBeans(List<DashboardChartAO> dashboardChartAOList) throws APIException {
		List<DashboardChartBean> ret = new ArrayList<>();
		if (dashboardChartAOList == null || dashboardChartAOList.size() == 0)
			return ret;

		List<DashboardAO> dashboardAOList = new ArrayList<>();
		for(String name : getDashboardAPI().getDashboardNames()) {
			dashboardAOList.add(getDashboardAPI().getDashboard(name));
		}
		for (DashboardChartAO dashboardChartAO : dashboardChartAOList) {
			String dashboardNames = "";
			for(DashboardAO dashboardAO: dashboardAOList) {
				if (dashboardAO.getCharts() == null || !dashboardAO.getCharts().contains(dashboardChartAO)) {
					dashboardNames += dashboardAO.getName()+",";
				}
			}
			if (dashboardNames.length() > 0)
				dashboardNames = dashboardNames.substring(0, dashboardNames.length()-1);
			ret.add(new DashboardChartBean(dashboardChartAO, dashboardNames));
		}

		return ret;
	}
}
