package net.anotheria.moskito.webcontrol.shared;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;
import net.anotheria.moskito.webcontrol.ui.shared.action.DashboardAction;
import net.anotheria.moskito.webcontrol.ui.shared.action.LoginAction;
import net.anotheria.moskito.webcontrol.ui.shared.action.LogoutAction;

/**
 * Actions mappings.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoWebControlActionMappings implements ActionMappingsConfigurator {

	/**
	 * Login page.
	 */
	private static final ActionForward LOGIN_PAGE = new ActionForward("login", "/net/anotheria/moskito/webcontrol/ui/shared/jsp/LoginPage.jsp");
	/**
	 * Dashboard page.
	 */
	private static final ActionForward DASHBOARD_PAGE = new ActionForward("html", "/net/anotheria/moskito/webcontrol/ui/shared/jsp/DashboardPage.jsp");

	@Override
	public void configureActionMappings() {
		// Security action's
		ActionMappings.addMapping(MoskitoWebControlActionConstants.LOGIN_ACTION, LoginAction.class, LOGIN_PAGE, DASHBOARD_PAGE);
		ActionMappings.addMapping(MoskitoWebControlActionConstants.LOGOUT_ACTION, LogoutAction.class);

		// Pages
		ActionMappings.addMapping(MoskitoWebControlActionConstants.ROOT_PAGE_ACTION, LoginAction.class, LOGIN_PAGE, DASHBOARD_PAGE);
		ActionMappings.addMapping(MoskitoWebControlActionConstants.DASHBOARD_PAGE_ACTION, DashboardAction.class, DASHBOARD_PAGE);

	}
}
