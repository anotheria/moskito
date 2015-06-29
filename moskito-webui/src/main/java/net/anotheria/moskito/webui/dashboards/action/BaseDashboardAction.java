package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.dashboards.bean.DashboardMenuItemBean;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;

/**
 * Base action for all dashboards action.
 *
 * @author lrosenberg
 * @since 12.02.15 14:02
 */
public abstract class BaseDashboardAction extends BaseMoskitoUIAction {
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.DASHBOARDS;
	}
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.preProcess(mapping, req, res);

		LinkedList<DashboardMenuItemBean> dashboardsMenu = new LinkedList<DashboardMenuItemBean>();

		//prepare list of dashboards
		MoskitoConfiguration config = MoskitoConfigurationHolder.getConfiguration();
		DashboardsConfig dashboardsConfig = config.getDashboardsConfig();
		if (dashboardsConfig.getDashboards()!=null){
			for (DashboardConfig dc : dashboardsConfig.getDashboards()){
				dashboardsMenu.add(new DashboardMenuItemBean(dc.getName()));
			}
		}

		req.setAttribute("dashboardsMenuItems", dashboardsMenu);
		req.setAttribute("selectedDashboard", getSelectedDashboard(req));
	}

	/**
	 * Returns currently selected dashboard.
	 * @param req the http servlet request.
	 * @return
	 * @throws APIException
	 */
	protected String getSelectedDashboard(HttpServletRequest req) throws APIException{
		String dashboardName = req.getParameter("dashboard");
		if (dashboardName!=null)
			return dashboardName;
		return getDashboardAPI().getDefaultDashboardName();
	}


}
