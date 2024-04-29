package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.08.16 21:44
 */
public class DashboardRemoveChartAction extends BaseDashboardAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String accNamesConcat = request.getParameter("pElement");
		String[] accNames = accNamesConcat.split(",");
		String dashboard = request.getParameter("pName");

		if (dashboard == null || accNames.length == 0) {
			setInfoMessage("Nothing selected!");
			return actionMapping.redirect();
		}

		getDashboardAPI().removeChartFromDashboard(dashboard, accNames);

		setInfoMessage(createInfoMessage(accNames, dashboard));

		return actionMapping.redirect()
				.addParameter("dashboard", dashboard);
	}

	private String createInfoMessage(String[] accNames, String dashboard) {
		if (accNames.length > 1)
			return "Accumulators " + StringUtils.concatenateTokens(", ", accNames) + "have been added to dashboard '"+dashboard+"\'";
		else
			return "Accumulator \'" + accNames[0] + "\' has been added to dashboard '"+dashboard+"\'";
	}
}
