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
public class DeleteDashboardAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dashboardName = request.getParameter("pName");

		if (!StringUtils.isEmpty(dashboardName)) {
			getDashboardAPI().removeDashboard(dashboardName);
		}

		setInfoMessage("Dashboard \'"+dashboardName+"\' has been deleted.");

		return mapping.redirect();
	}
}
