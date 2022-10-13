package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.tracers.api.TracerAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Shows currently active tracers.
 *
 * @author lrosenberg
 * @since 05.05.15 00:41
 */
public class ShowTracersAction extends BaseTracersAction{
	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

		List<TracerAO> tracers = getTracerAPI().getTracers();
		httpServletRequest.setAttribute("tracersCount", tracers.size());
		httpServletRequest.setAttribute("tracers", tracers);

		return actionMapping.success();
	}
}
