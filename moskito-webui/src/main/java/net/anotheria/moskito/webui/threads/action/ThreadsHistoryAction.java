package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.threads.api.ActiveThreadHistoryAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This action shows the ThreadsHistory.
 */
public class ThreadsHistoryAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ActiveThreadHistoryAO history = getThreadAPI().getActiveThreadHistory();
		
		req.setAttribute("active", Boolean.valueOf(history.isActive()));
		req.setAttribute("listsize", history.getListSize());
		
		if (!history.isActive())
			return mapping.success();
		
		req.setAttribute("history", history.getEvents());
		
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "threads_history";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.THREADS_HISTORY;
	}

}
