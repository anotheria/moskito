package net.anotheria.moskito.webui.nowrunning.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.nowrunning.api.EntryPointAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20
 */
public class ShowEntryPointsAction extends BaseNowRunningAction {

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
								 HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<EntryPointAO> entryPoints = getNowRunningAPI().getEntryPoints();
		req.setAttribute("entryPoints", entryPoints);

		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskNowRunning?ts="+System.currentTimeMillis();
	}


	@Override
	protected String getPageName() {
		return "nowrunning";
	}


}
