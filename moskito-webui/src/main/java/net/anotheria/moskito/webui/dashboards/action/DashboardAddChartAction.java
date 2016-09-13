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
		String accNamesConcat = request.getParameter("pName");
		String[] accNames = accNamesConcat.split(",");
		String[] dashboardsName = request.getParameterValues("pDashboards");

		if (dashboardsName == null || dashboardsName.length == 0 || accNames.length == 0) {
			setInfoMessage("Nothing selected!");
			return mapping.redirect();
		}

		for(String dashboard : dashboardsName) {
			getDashboardAPI().addChartToDashboard(dashboard, accNames);
		}

		setInfoMessage(createInfoMessage(accNames, dashboardsName));

		return mapping.redirect().addParameter("dashboard", dashboardsName[0]);
	}

	private String createInfoMessage(String[] accNames, String[] dashboardsName) {
		String accums;
		if (accNames.length > 1)
			accums = "Accumulators " + StringUtils.concatenateTokens(", ", accNames) + " have been added to ";
		else
			accums = "Accumulator \'" + accNames[0] + "\' has been added to ";

		String dashboards;
		if (dashboardsName.length > 1)
			dashboards = "following dashboards: "+ StringUtils.concatenateTokens(", ", dashboardsName);
		else
			dashboards = "dashboard \'"+ dashboardsName[0] + "\'";
		return accums + dashboards;
	}
}
