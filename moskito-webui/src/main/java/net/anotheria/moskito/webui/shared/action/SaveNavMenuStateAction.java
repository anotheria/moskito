package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.json.JSONResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Save nav menu state action.
 *
 * @author Vasyl Zarva
 */
public class SaveNavMenuStateAction extends BaseAJAXMoskitoUIAction {

	/**
	 * Request param: indication if menu navigation is collapsed or not.
	 */
	public static final String PARAM_IS_NAV_MENU_COLLAPSED = "isNavMenuCollapsed";

	/**
	 * Attribute name: indication if menu navigation is collapsed or not.
	 */
	public static final String ATTR_IS_NAV_MENU_COLLAPSED = "isNavMenuCollapsed";

	@Override
	protected void invokeExecute(ActionMapping mapping,  HttpServletRequest req, HttpServletResponse res, JSONResponse jsonResponse) throws Exception {
		final String navMenuCollapseStateParam = req.getParameter(PARAM_IS_NAV_MENU_COLLAPSED);

		req.getSession().setAttribute(ATTR_IS_NAV_MENU_COLLAPSED, Boolean.valueOf(navMenuCollapseStateParam));
	}
}
