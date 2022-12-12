package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Creates a new tracer.
 *
 * @author lrosenberg
 * @since 05.05.15 16:25
 */
public class CreateTracerAction extends BaseTracersAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String producerId = httpServletRequest.getParameter(PARAM_PRODUCER_ID);
		String statName = httpServletRequest.getParameter("pStatName");
		getTracerAPI().createTracer(producerId, statName);
		httpServletResponse.sendRedirect("mskShowProducer?"+PARAM_PRODUCER_ID+ '=' + URLEncoder.encode(producerId, "UTF-8"));
		return null;
	}
}