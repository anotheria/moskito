package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;
import net.java.dev.moskito.core.timing.IUpdateable;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
 * This action fires an interval update. This is useful for intervals that are not updated by a timer, like snapshots.
 * @author another
 *
 */
public class ForceIntervalUpdateAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String intervalName = req.getParameter(PARAM_INTERVAL);
		if (intervalName==null || intervalName.length()==0)
			throw new IllegalArgumentException("Interval name missing");
		
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
		
		String reply = "updated "+interval.toString();
		res.getOutputStream().write(reply.getBytes("UTF-8"));
		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}
		
}
