package net.anotheria.moskito.webcontrol.ui.shared.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlActionConstants;
import net.anotheria.webutils.util.CookieUtil;

/**
 * Logout action.
 * 
 * @author Alexandr Bolbat
 */
public class LogoutAction extends BasicAction {

	@Override
	protected String getActionName() {
		return MoskitoWebControlActionConstants.LOGOUT_ACTION;
	}

	@Override
	protected ActionCommand invokeExecute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		getLoginAPI().logoutMe();
		CookieUtil.setCookie(res, LoginAction.USERNAME_PASSWORD_COOKIE, "", LoginAction.COOKIE_EXPIRE_TIME);
		res.sendRedirect(MoskitoWebControlActionConstants.LOGIN_ACTION);
		throw new AbortExecutionException();
	}

}
