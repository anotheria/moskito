package net.anotheria.moskito.webui.shared.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.json.JSONResponse;
import net.anotheria.util.StringUtils;
import org.json.JSONException;

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
	protected void invokeExecute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res, JSONResponse jsonResponse) throws AbortExecutionException, IOException, JSONException {
		final String navMenuCollapseStateParam = req.getParameter(PARAM_IS_NAV_MENU_COLLAPSED);

		req.getSession().setAttribute(ATTR_IS_NAV_MENU_COLLAPSED, Boolean.valueOf(navMenuCollapseStateParam));
	}
}
