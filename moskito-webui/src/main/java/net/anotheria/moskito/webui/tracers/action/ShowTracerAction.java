package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.tracer.Tracers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Prepares a single tracer for display.
 *
 * @author lrosenberg
 * @since 05.05.15 18:41
 */
public class ShowTracerAction extends BaseTracersAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

		String producerId = httpServletRequest.getParameter(PARAM_PRODUCER_ID);
		String journeyName = Tracers.getJourneyNameForTracers(producerId);
		httpServletRequest.setAttribute("journeyName", journeyName);
		httpServletRequest.setAttribute("tracer", getTracerAPI().getTracer(producerId));
		httpServletRequest.setAttribute("traces", getTracerAPI().getTraces(producerId, getCurrentUnit(httpServletRequest).getUnit()));

		return actionMapping.success();

	}
}
