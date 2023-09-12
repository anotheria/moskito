package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author strel
 */
public class DashboardAddProducerAction extends BaseDashboardAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String producerName = request.getParameter("pName");
		String[] dashboardsName = request.getParameterValues("pDashboards");

		if (dashboardsName == null || dashboardsName.length == 0) {
			setInfoMessage("Nothing selected!");
			return mapping.redirect();
		}

		for (String dashboard : dashboardsName) {
			getDashboardAPI().addProducerToDashboard(dashboard, producerName);
		}

		setInfoMessage(createInfoMessage(producerName, dashboardsName));

		return mapping.redirect().addParameter("dashboard", dashboardsName[0]);
	}

	private String createInfoMessage(String producerName, String[] dashboardsName) {
		if (dashboardsName.length > 1)
			return "Producer \'" + producerName + "\' has been added to following dashboards: " + StringUtils.concatenateTokens(", ", dashboardsName);
		else
			return "Producer \'" + producerName + "\' has been added to dashboard \'" + dashboardsName[0] + "\'";
	}
}
