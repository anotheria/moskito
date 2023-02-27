package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 * @author lrosenberg
 */
public class CreateDashboardAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dashboardName = request.getParameter("pName");

		if (!StringUtils.isEmpty(dashboardName)) {
			getDashboardAPI().createDashboard(dashboardName);
		}

		setInfoMessage("Dashboard \'"+dashboardName+"\' has been created.");

		return mapping.redirect().addParameter("dashboard", dashboardName);
	}
}
