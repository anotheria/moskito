package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 * @author lrosenberg
 */
public class DashboardAddGaugeAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String gaugeName = request.getParameter("pName");
		String[] dashboardsName = request.getParameterValues("pDashboards");

		if (dashboardsName == null || dashboardsName.length == 0) {
			setInfoMessage("Nothing selected!");
			return mapping.redirect();
		}

		for(String dashboard : dashboardsName) {
			getDashboardAPI().addGaugeToDashboard(dashboard, gaugeName);
		}

		setInfoMessage(createInfoMessage(gaugeName, dashboardsName));

		return mapping.redirect().addParameter("dashboard", dashboardsName[0]);
	}

	private String createInfoMessage(String gaugeName, String[] dashboardsName) {
		if (dashboardsName.length > 1)
			return "Gauge \'"+gaugeName+"\' has been added to following dashboards: "+ StringUtils.concatenateTokens(", ", dashboardsName);
		else
			return "Gauge \'"+gaugeName+"\' has been added to dashboard \'"+ dashboardsName[0] + "\'";
	}
}
