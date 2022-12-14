package net.anotheria.moskito.webui.tracers.action;

import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.tracers.api.TracerAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * Base action for tracers actions.
 *
 * @author lrosenberg
 * @since 05.05.15 00:15
 */
public abstract class BaseTracersAction extends BaseMoskitoUIAction {

	/**
	 * Parameter name for tracer id.
	 */
	public static final String PARAM_TRACER_ID = "pTracerId";

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return null;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.TRACERS;
	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.preProcess(mapping, req, res);

		//Add tracers submenu.
		List<TracerAO> tracers = getTracerAPI().getTracers();
		List<String> tracerSubmenuNames = new LinkedList<>();
		for (TracerAO ao : tracers){
			tracerSubmenuNames.add(ao.getTracerId());
		}
		req.setAttribute("tracerSubmenuNames", tracerSubmenuNames);

	}
}
