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

		String accNamesConcat = request.getParameter("pElement");
		String[] accNames = accNamesConcat.split(",");
		String dashboard = request.getParameter("pName");

		getDashboardAPI().removeChartFromDashboard(dashboard, accNames);

		setInfoMessage("Accumulators \'"+accNamesConcat+"\' has been removed from dashboard \'"+dashboard+"\'");

		return actionMapping.redirect()
				.addParameter("dashboard", dashboard);
	}
}
