package net.anotheria.moskito.webui.shared.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.maf.json.JSONResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract AJAX action.
 *
 * @author Vasyl Zarva
 */
public abstract class BaseAJAXMoskitoUIAction implements Action {

	/**
	 * {@code Log4j} {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAJAXMoskitoUIAction.class);

	/**
	 * Encoding.
	 */
	private static final String UTF_8 = "UTF-8";

	/**
	 * Content type.
	 */
	private static final String TEXT_X_JSON = "application/json";

	/**
	 * Internal server error message.
	 */
	public static final String ERROR_MSG_INTERNAL_SERVER_ERROR = "Internal server error";

	/**
	 * Performs validation. Override this method in subclasses.
	 * All errors should be reflected in provided {@link JSONResponse}.
	 *
	 * @param req
	 * 		{@link HttpServletRequest}
	 * @param response
	 * 		{@link JSONResponse}
	 */
	protected void validate(final HttpServletRequest req, final JSONResponse response) {
		// do nothing by default

		if (LOGGER.isTraceEnabled())
			LOGGER.trace("Nothing to validate. Skipping.");
	}

	@Override
	public final void preProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws AbortExecutionException {
		// Invoking real preProcess
		try {
			invokePreProcess(mapping, req, res);
		} catch (final IOException e) {
			throw new AbortExecutionException();
		} catch (final JSONException e) {
			throw new AbortExecutionException();
		}
	}

	@Override
	public final ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws AbortExecutionException {
		JSONResponse response = new JSONResponse();

		validate(req, response);

		try {
			if (response.hasErrors()) {
				writeTextToResponse(res, response);
				return null;
			}

			invokeExecute(mapping, bean, req, res, response);
			writeTextToResponse(res, response);
			return null;
		} catch (Exception e) {
			LOGGER.error("execute(mapping, bean, req, res) fail.", e);
			JSONResponse result = new JSONResponse();
			result.addError(ERROR_MSG_INTERNAL_SERVER_ERROR);
			try {
				writeTextToResponse(res, result);
				return null;
			} catch (final IOException ioe) {
				LOGGER.error("execute(mapping,bean,req,res) fail.", ioe);
				throw new AbortExecutionException();
			}
		}
	}

	@Override
	public final void postProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws AbortExecutionException {
		// Invoking real postProcess
		invokePostProcess(mapping, req, res);
	}

	/**
	 * Override this method for invoking preProcess code.
	 *
	 * @param mapping
	 * 		- action mapping
	 * @param req
	 * 		- request
	 * @param res
	 * 		- response
	 * @throws AbortExecutionException
	 * @throws IOException
	 * @throws JSONException
	 */
	protected void invokePreProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws AbortExecutionException,
			IOException, JSONException {
		// do nothing by default

		if (LOGGER.isTraceEnabled())
			LOGGER.trace("Nothing to preProcess. Skipping.");
	}

	/**
	 * Override this method for invoking main action code.
	 *
	 * @param mapping
	 * 		- action mapping
	 * @param bean
	 * 		- bean
	 * @param req
	 * 		- request
	 * @param res
	 * 		- response
	 * @param jsonResponse
	 * 		- JSON Response
	 * @throws AbortExecutionException
	 * @throws JSONException
	 * @throws IOException
	 */
	protected void invokeExecute(final ActionMapping mapping, final FormBean bean, final HttpServletRequest req, final HttpServletResponse res, final JSONResponse jsonResponse)
			throws AbortExecutionException, IOException, JSONException {

		if (LOGGER.isTraceEnabled())
			LOGGER.trace("You should override this method.");
	}

	/**
	 * Override this method for invoking postProcess code.
	 *
	 * @param mapping
	 * 		- action mapping
	 * @param req
	 * 		- request
	 * @param res
	 * 		- response
	 * @throws AbortExecutionException
	 */
	protected void invokePostProcess(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res) throws AbortExecutionException {
		// do nothing by default

		if (LOGGER.isTraceEnabled())
			LOGGER.trace("Nothing to postProcess. Skipping.");
	}

	/**
	 * Writes specified text to response and flushes the stream.
	 *
	 * @param res
	 * 		{@link HttpServletRequest}
	 * @param jsonResponse
	 * 		{@link JSONResponse}
	 * @throws java.io.IOException
	 * 		if an input or output exception occurred
	 */
	private static void writeTextToResponse(final HttpServletResponse res, final JSONResponse jsonResponse) throws IOException {
		writeTextToResponse(res, jsonResponse.toString());
	}

	/**
	 * Writes specified text to response and flushes the stream.
	 *
	 * @param res
	 * 		{@link HttpServletRequest}
	 * @param text
	 * 		{@link String}
	 * @throws java.io.IOException
	 * 		if an input or output exception occurred
	 */
	private static void writeTextToResponse(final HttpServletResponse res, final String text) throws IOException {
		res.setCharacterEncoding(UTF_8);
		res.setContentType(TEXT_X_JSON);
		PrintWriter writer = res.getWriter();
		writer.write(text);
		writer.flush();
	}
}
