package net.anotheria.moskito.webui.dashboards.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.dashboards.bean.DashboardMenuItemBean;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
		return "?ts="+System.currentTimeMillis();
	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.preProcess(mapping, req, res);

		Deque<DashboardMenuItemBean> dashboardsMenu = new LinkedList<>();

		//prepare list of dashboards
		List<String> names = getDashboardAPI().getDashboardNames();
		for (String name : names){
			dashboardsMenu.add(new DashboardMenuItemBean(name));
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
	protected String getSelectedDashboard(ServletRequest req) throws APIException{
		String dashboardName = req.getParameter("dashboard");
		if (dashboardName!=null)
			return dashboardName;
		return getDashboardAPI().getDefaultDashboardName();
	}


}
