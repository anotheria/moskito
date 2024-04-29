package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Disables a tracer.
 *
 * @author lrosenberg
 * @since 05.05.15 17:09
 */
public class DisableTracerAction extends BaseTracersAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String tracerId = httpServletRequest.getParameter(PARAM_TRACER_ID);
		getTracerAPI().disableTracer(tracerId);
		return actionMapping.redirect();
	}
}