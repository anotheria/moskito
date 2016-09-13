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
public class DashboardAddThresholdAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String thresholdName = request.getParameter("pName");
		String[] dashboardsName = request.getParameterValues("pDashboards");

		if (dashboardsName == null || dashboardsName.length == 0) {
			setInfoMessage("Nothing selected!");
			return mapping.redirect();
		}

		for (String dashboard : dashboardsName) {
			getDashboardAPI().addThresholdToDashboard(dashboard, thresholdName);
		}

		setInfoMessage(createInfoMessage(thresholdName, dashboardsName));

		return mapping.redirect().addParameter("dashboard", dashboardsName[0]);
	}

	private String createInfoMessage(String thresholdName, String[] dashboardsName) {
		if (dashboardsName.length > 1)
			return "Threshold \'" + thresholdName + "\' has been added to following dashboards: " + StringUtils.concatenateTokens(", ", dashboardsName);
		else
			return "Threshold \'" + thresholdName + "\' has been added to dashboard \'" + dashboardsName[0] + "\'";
	}
}
