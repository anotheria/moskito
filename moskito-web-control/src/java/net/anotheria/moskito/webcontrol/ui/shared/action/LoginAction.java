package net.anotheria.moskito.webcontrol.ui.shared.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlActionConstants;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlConstants;
import net.anotheria.moskito.webcontrol.ui.shared.bean.ErrorMessageBean;
import net.anotheria.util.StringUtils;
import net.anotheria.util.crypt.CryptTool;
import net.anotheria.webutils.util.CookieUtil;

/**
 * Login action.
 * 
 * @author Alexandr Bolbat
 */
public class LoginAction extends BasicAction {
	/**
	 * User name empty error.
	 */
	public static final ErrorMessageBean ERROR_USERNAME_EMPTY = new ErrorMessageBean("login", "Please enter a username");
	/**
	 * Password empty error.
	 */
	public static final ErrorMessageBean ERROR_PASSWORD_EMPTY = new ErrorMessageBean("password", "Please enter a password");
	/**
	 * Invalid user error.
	 */
	public static final ErrorMessageBean ERROR_INVALID_USER = new ErrorMessageBean("login", "Invalid username");
	/**
	 * Invalid password error.
	 */
	public static final ErrorMessageBean ERROR_INVALID_PASSWORD = new ErrorMessageBean("password", "Invalid password");
	/**
	 * Attribute user name.
	 */
	public static final String ATTR_USERNAME = "username";
	/**
	 * Attribute password.
	 */
	public static final String ATTR_PASSWORD = "password";
	/**
	 * Authorization string delimiter.
	 */
	public static final char AUTH_DELIMITER = ':';
	/**
	 * User name cookie.
	 */
	public static final String USERNAME_COOKIE = "mwcun"; // m_oskito w_eb c_ontrol u_ser n_ame
	/**
	 * User name and password cookie.
	 */
	public static final String USERNAME_PASSWORD_COOKIE = "mwcunap"; // m_oskito w_eb c_ontrol u_ser n_ame a_nd p_assword
	/**
	 * Cookie expiration time.
	 */
	public static final int COOKIE_EXPIRE_TIME = 60 * 60 * 24 * 30; // 30 days in seconds
	/**
	 * Crypt utility.
	 */
	private static final CryptTool CRYPT_TOOL = new CryptTool("97531f6c04afcbd529028f3f45221ffd");

	@Override
	protected String getActionName() {
		return MoskitoWebControlActionConstants.LOGIN_ACTION;
	}

	@Override
	protected ActionCommand invokeExecute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (getLoginAPI().isLogedIn()) {
			res.sendRedirect(MoskitoWebControlActionConstants.DASHBOARD_PAGE_ACTION);
			throw new AbortExecutionException();
		}

		String loginParam = "";
		String passwordParam = "";

		if (req.getMethod().equalsIgnoreCase("POST")) {
			loginParam = req.getParameter("login");
			req.setAttribute(ATTR_USERNAME, loginParam);
			passwordParam = req.getParameter("password");
			req.setAttribute(ATTR_PASSWORD, passwordParam);
		}

		if (req.getMethod().equalsIgnoreCase("GET")) {
			String cookieUsername = CookieUtil.getCookieValue(req, USERNAME_COOKIE);
			req.setAttribute(ATTR_USERNAME, cookieUsername);

			// if no auto login cookies
			String authString = CookieUtil.getCookieValue(req, USERNAME_PASSWORD_COOKIE);
			if (StringUtils.isEmpty(authString))
				return mapping.findCommand("login");

			// try to perform auto login
			String decodedAuthString = CRYPT_TOOL.decryptFromHex(authString).trim();
			int index = decodedAuthString.indexOf(AUTH_DELIMITER);
			if (index != -1) {
				loginParam = decodedAuthString.substring(0, index);
				passwordParam = decodedAuthString.substring(index + 1);
			}
		}

		// is user can login check
		if (!isCanLogin(loginParam, passwordParam, req))
			return mapping.findCommand("login");

		// perform user login
		performLogin(loginParam, passwordParam, req, res);
		throw new AbortExecutionException();
	}

	/**
	 * Login execution.
	 * 
	 * @param loginParam
	 *            - login
	 * @param passwordParam
	 *            - password
	 * @param req
	 *            - request
	 * @param res
	 *            - response
	 * @throws IOException
	 * @throws APIException
	 */
	private void performLogin(String loginParam, String passwordParam, HttpServletRequest req, HttpServletResponse res) throws IOException, APIException {
		getLoginAPI().logInUser(loginParam);
		CookieUtil.setCookie(res, USERNAME_COOKIE, loginParam, COOKIE_EXPIRE_TIME);
		CookieUtil.setCookie(res, USERNAME_PASSWORD_COOKIE, prepareAuthString(loginParam, passwordParam), COOKIE_EXPIRE_TIME);

		Object redirectAction = req.getSession().getAttribute(MoskitoWebControlConstants.REQUEST_PARAM_ACTION);
		if (redirectAction instanceof String) {
			req.getSession().removeAttribute(MoskitoWebControlConstants.REQUEST_PARAM_ACTION);
			String queryString = !StringUtils.isEmpty(req.getQueryString()) ? "?" + req.getQueryString() : "";
			res.sendRedirect(String.class.cast(redirectAction) + queryString); // redirecting to right place after auto login
			return;
		}

		res.sendRedirect(MoskitoWebControlActionConstants.DASHBOARD_PAGE_ACTION);
	}

	/**
	 * Utility method for preparing authorization string.
	 * 
	 * @param loginParam
	 *            - login
	 * @param passwordParam
	 *            - password
	 * @return {@link String}
	 */
	protected static String prepareAuthString(String loginParam, String passwordParam) {
		return CRYPT_TOOL.encryptToHex(loginParam + AUTH_DELIMITER + passwordParam);
	}

	/**
	 * Is can user login with given parameters.
	 * 
	 * @param loginParam
	 *            - login
	 * @param passwordParam
	 *            - password
	 * @param req
	 *            - request
	 * @return <code>true</code> if can or <code>false</code>
	 */
	private boolean isCanLogin(String loginParam, String passwordParam, HttpServletRequest req) {
		if (StringUtils.isEmpty(loginParam)) {
			setErrorMessage(req, ERROR_USERNAME_EMPTY);
			return false;
		}

		if (StringUtils.isEmpty(passwordParam)) {
			setErrorMessage(req, ERROR_PASSWORD_EMPTY);
			return false;
		}

		// if (!getAccessAPI().isUserExists(loginParam)) {
		// setErrorMessage(req, ERROR_INVALID_USER);
		// return false;
		// }
		//
		// if (!getAccessAPI().isUserPasswordMatch(loginParam, passwordParam)) {
		// setErrorMessage(req, ERROR_INVALID_PASSWORD);
		// return false;
		// }

		return true;
	}

	/**
	 * Set error message.
	 * 
	 * @param req
	 *            - request
	 * @param bean
	 *            - error bean
	 */
	private void setErrorMessage(HttpServletRequest req, ErrorMessageBean bean) {
		req.setAttribute(ErrorMessageBean.BEAN_NAME, bean);
	}

}
