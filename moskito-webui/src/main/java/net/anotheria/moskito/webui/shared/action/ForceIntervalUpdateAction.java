package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		if (intervalName==null || intervalName.isEmpty())
			throw new IllegalArgumentException("Interval name missing");

		APILookupUtility.getAdditionalFunctionalityAPI().forceIntervalUpdate(intervalName);

		String reply = "OK";
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
