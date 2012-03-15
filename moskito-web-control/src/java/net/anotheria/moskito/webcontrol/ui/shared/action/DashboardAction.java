package net.anotheria.moskito.webcontrol.ui.shared.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlActionConstants;

/**
 * Dashboard action.
 * 
 * @author Alexandr Bolbat
 */
public class DashboardAction extends BasicAction {

	/**
	 * Attribute.
	 */
	public static final String ATTR_LOGGED_USER = "mwc.logged.user";

	@Override
	protected String getActionName() {
		return MoskitoWebControlActionConstants.DASHBOARD_PAGE_ACTION;
	}

	@Override
	protected boolean isSecured() {
		return true;
	}

	@Override
	protected void invokePreProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute(ATTR_LOGGED_USER, getLoginAPI().getLogedUserId());
	}

}
