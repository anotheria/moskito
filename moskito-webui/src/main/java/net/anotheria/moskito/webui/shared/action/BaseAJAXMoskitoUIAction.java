package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.AbortExecutionException;
import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.json.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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


	@Override
	public final ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws AbortExecutionException {
		JSONResponse response = new JSONResponse();

		try {

			invokeExecute(mapping, req, res, response);
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

	/**
	 * Override this method for invoking main action code.
	 *
	 * @param mapping
	 * 		- action mapping
	 * @param req
	 * 		- request
	 * @param res
	 * 		- response
	 * @param jsonResponse
	 * 		- JSON Response
	 * @throws Exception on errors
	 */
	protected void invokeExecute(final ActionMapping mapping, final HttpServletRequest req, final HttpServletResponse res, final JSONResponse jsonResponse)
			throws Exception {

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

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}
}
