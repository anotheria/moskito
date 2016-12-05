package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Enables a tracer.
 *
 * @author lrosenberg
 * @since 05.05.15 17:09
 */
public class EnableTracerAction extends BaseTracersAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String producerId = httpServletRequest.getParameter(PARAM_PRODUCER_ID);
		getTracerAPI().enableTracer(producerId);
		return actionMapping.redirect();
	}
}