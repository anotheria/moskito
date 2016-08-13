package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.08.16 21:44
 */
public class DashboardRemoveChartAction extends BaseDashboardAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {

		int chart = Integer.parseInt(request.getParameter("chart"));
		String dashboard = request.getParameter("dashboard");

		System.out.println("Remove "+chart+ " from "+dashboard);

		getDashboardAPI().removeChartFromDashboard(dashboard, chart);

		response.sendRedirect("mskDashboard?dashboard="+dashboard);
		return null;
	}
}
