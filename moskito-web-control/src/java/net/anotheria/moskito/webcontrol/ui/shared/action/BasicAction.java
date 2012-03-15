package net.anotheria.moskito.webcontrol.ui.shared.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoplass.api.generic.login.LoginAPI;
import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.json.JSONResponse;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlActionConstants;
import net.anotheria.moskito.webcontrol.shared.MoskitoWebControlConstants;
import net.anotheria.util.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic action.
 * 
 * @author Alexandr Bolbat
 */
public abstract class BasicAction implements Action {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAction.class);

	/**
	 * Encoding.
	 */
	private static final String UTF_8 = "UTF-8";

	/**
	 * Content type.
	 */
	private static final String TEXT_X_JSON = "text/x-json";

	/**
	 * {@link LoginAPI} instance.
	 */
	private static final LoginAPI LOGIN_API = APIFinder.findAPI(LoginAPI.class);

	/**
	 * Internal server error message.
	 */
	protected static final String INTERNAL_SERVER_ERROR_MSG = "Internal server error";

	/**
	 * Override this method if we need to use action only for logged in user.
	 * 
	 * @return
	 */
	protected boolean isSecured() {
		return false;
	}

	/**
	 * Action is for AJAX communication with JSON response.
	 * 
	 * @return <code>true</code> if AJAX or <code>false</code>
	 */
	protected boolean isAJAX() {
		return false;
	}

	/**
	 * Action name.
	 * 
	 * @return {@link String}
	 */
	protected abstract String getActionName();

	/**
	 * Security check.
	 * 
	 * @param req
	 *            - request
	 * @param res
	 *            - response
	 * @throws AbortExecutionException
	 * @throws IOException
	 */
	private void canExecute(final HttpServletRequest req, final HttpServletResponse res) throws AbortExecutionException, IOException {
		if (!isSecured())
			return;

		if (!LOGIN_API.isLogedIn()) {
			if (!isAJAX()) {
				req.getSession().setAttribute(MoskitoWebControlConstants.REQUEST_PARAM_ACTION, getActionName()); // setting action name for redirection for
																													// automatic login
				String queryString = !StringUtils.isEmpty(req.getQueryString()) ? "?" + req.getQueryString() : "";
				res.sendRedirect(MoskitoWebControlActionConstants.LOGIN_ACTION + queryString);
				throw new AbortExecutionException();
			}
			JSONResponse result = new JSONResponse();
			result.addError("Not Logged In.");
			result.addCommand("Redirect", MoskitoWebControlActionConstants.LOGIN_ACTION);
			writeTextToResponse(res, result);
			throw new AbortExecutionException();
		}

		if (StringUtils.isEmpty(getActionName())) {
			String message = "canExecute(req, res) fail. Wrong action name[" + getActionName() + "].";
			LOGGER.warn(message);
			throw new AbortExecutionException();
		}
	}

	@Override
	public final void preProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws Exception {
		// Security check
		canExecute(req, res);

		// Populate logged user full name to request attribute
		if (req.getAttribute(MoskitoWebControlConstants.REQUEST_ATTR_LOGGED_USER) == null && LOGIN_API.isLogedIn())
			req.setAttribute(MoskitoWebControlConstants.REQUEST_ATTR_LOGGED_USER, LOGIN_API.getLogedUserId());

		// Invoking real preProcess
		invokePreProcess(mapping, req, res);
	}

	/**
	 * Override this method for invoking preProcess code.
	 * 
	 * @param mapping
	 *            - action mapping
	 * @param req
	 *            - request
	 * @param res
	 *            - response
	 * @throws Exception
	 */
	protected void invokePreProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws Exception {
	}

	@Override
	public final ActionCommand execute(final ActionMapping mapping, final FormBean bean, final HttpServletRequest req, final HttpServletResponse res)
			throws Exception {
		// Security check
		canExecute(req, res);

		// Invoking real execute
		try {
			return invokeExecute(mapping, bean, req, res);
		} catch (Exception e) {
			if (!isAJAX())
				throw e;

			LOGGER.error("execute(mapping, bean, req, res) fail.", e);
			JSONResponse result = new JSONResponse();
			result.addError(INTERNAL_SERVER_ERROR_MSG);
			writeTextToResponse(res, result);
			return null;
		}
	}

	/**
	 * Override this method for invoking main action code.
	 * 
	 * @param mapping
	 *            - action mapping
	 * @param bean
	 *            - bean
	 * @param req
	 *            - request
	 * @param res
	 *            - response
	 * @return - {@link ActionForward}
	 * @throws Exception
	 */
	protected ActionCommand invokeExecute(final ActionMapping mapping, final FormBean bean, final HttpServletRequest req, final HttpServletResponse res)
			throws Exception {
		return mapping.findCommand("html");
	}

	@Override
	public final void postProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws Exception {
		// Security check
		canExecute(req, res);

		// Invoking real postProcess
		invokePostProcess(mapping, req, res);
	}

	/**
	 * Override this method for invoking postProcess code.
	 * 
	 * @param mapping
	 *            - action mapping
	 * @param req
	 *            - request
	 * @param res
	 *            - response
	 * @throws Exception
	 */
	protected void invokePostProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws Exception {
	}

	/**
	 * Get {@link LoginAPI} instance.
	 * 
	 * @return {@link LoginAPI}
	 */
	protected LoginAPI getLoginAPI() {
		return LOGIN_API;
	}

	/**
	 * Writes specified text to response and flushes the stream.
	 * 
	 * @param res
	 *            {@link HttpServletRequest}
	 * @param jsonResponse
	 *            {@link JSONResponse}
	 * @throws java.io.IOException
	 *             if an input or output exception occurred
	 */
	protected static void writeTextToResponse(final HttpServletResponse res, final JSONResponse jsonResponse) throws IOException {
		res.setCharacterEncoding(UTF_8);
		res.setContentType(TEXT_X_JSON);
		PrintWriter writer = res.getWriter();
		writer.write(jsonResponse.toString());
		writer.flush();
	}

	/**
	 * Utility method for validating {@link String} request parameter and writing {@link JSONResponse} with error message to response.
	 * 
	 * @param value
	 *            - {@link String} value for validating
	 * @param valueName
	 *            - name of the parameter
	 * @param minLength
	 *            - minimum length of value, if 0 - value can be <code>null</code> or empty
	 * @param maxLength
	 *            - maximum length of value
	 * @param res
	 *            - {@link HttpServletResponse}
	 * @return <code>true</code> if value valid or <code>false</code>
	 * @throws IOException
	 */
	protected static final boolean validateParam(String value, String valueName, int minLength, int maxLength, HttpServletResponse res) throws IOException {
		if (minLength != 0 && StringUtils.isEmpty(value)) {
			JSONResponse result = new JSONResponse();
			result.addError(valueName, StringUtils.capitalize(valueName) + "[" + value + "] is empty.");
			writeTextToResponse(res, result);
			return false;
		}

		if (value != null && value.length() < minLength) {
			JSONResponse result = new JSONResponse();
			result.addError(valueName, StringUtils.capitalize(valueName) + "[" + value + "] length less then possible minimum[" + minLength + "].");
			writeTextToResponse(res, result);
			return false;
		}

		if (value != null && value.length() > maxLength) {
			JSONResponse result = new JSONResponse();
			result.addError(valueName, StringUtils.capitalize(valueName) + "[" + value + "] length more then possible maximum[" + maxLength + "].");
			writeTextToResponse(res, result);
			return false;
		}

		return true;
	}

}
