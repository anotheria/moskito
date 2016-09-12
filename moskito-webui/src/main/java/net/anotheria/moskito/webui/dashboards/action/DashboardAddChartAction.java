package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.action.CommandRedirect;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 * @author lrosenberg
 */
public class DashboardAddChartAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String chartName = request.getParameter("pName");
		String[] dashboardsName = request.getParameterValues("pDashboards");

		for(String dashboard : dashboardsName) {
			getDashboardAPI().addChartToDashboard(dashboard, chartName);
		}

		setInfoMessage( "Chart \'"+chartName+"\' has been added to following dashboards: "+ StringUtils.concatenateTokens(", ", dashboardsName));

		CommandRedirect commandRedirect =  mapping.redirect();
		if (dashboardsName.length == 1) {
			commandRedirect = commandRedirect.addParameter("dashboard", dashboardsName[0]);
		}
		return commandRedirect;
	}
}