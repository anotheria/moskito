package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		if (selectedDashboard == null){
			return actionMapping.success();
		}

		DashboardAO dashboard = getDashboardAPI().getDashboard(dashboardName);

		//now we definitely have a selected dashboard.
		//prepare thresholds
		if (dashboard.getThresholds()!=null && dashboard.getThresholds().size()>0){
			request.setAttribute("thresholds", dashboard.getThresholds());
			thresholdsPresent = true;
		}

		//prepare gauges
		if (dashboard.getGauges()!=null && dashboard.getGauges().size()>0){
			request.setAttribute("gauges", dashboard.getGauges());
			gaugesPresent = true;
		}

		//charts gauges
		if (dashboard.getCharts()!=null && dashboard.getCharts().size()>0){
			request.setAttribute("charts", dashboard.getCharts());
			chartsPresent = true;
		}


		//maybe the value has changed.
		request.setAttribute("gaugesPresent", gaugesPresent);
		request.setAttribute("chartsPresent", chartsPresent);
		request.setAttribute("thresholdsPresent", thresholdsPresent);
		request.setAttribute("showHelp", !(gaugesPresent || chartsPresent || thresholdsPresent));


		return actionMapping.success();
	}

	@Override
	protected String getPageName() {
		return "dashboard";
	}

}
