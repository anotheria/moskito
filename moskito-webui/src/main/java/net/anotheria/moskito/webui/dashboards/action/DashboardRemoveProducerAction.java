package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author strel
 */
public class DashboardRemoveProducerAction extends BaseDashboardAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String elementName = request.getParameter("pElement");
		String dashboard = request.getParameter("pName");

		if (dashboard == null || elementName == null) {
			setInfoMessage("Nothing selected!");
			return actionMapping.redirect();
		}

		getDashboardAPI().removeProducerFromDashboard(dashboard, elementName);

		setInfoMessage("Producer \'"+elementName+"\' has been removed from dashboard \'"+dashboard+"\'");

		return actionMapping.redirect().addParameter("dashboard", dashboard);
	}
}
