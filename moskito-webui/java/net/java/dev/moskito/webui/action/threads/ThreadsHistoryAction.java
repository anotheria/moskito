package net.java.dev.moskito.webui.action.threads;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.util.threadhistory.ThreadHistoryUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action shows the ThreadsHistory.
 */
public class ThreadsHistoryAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		boolean historyOn = ThreadHistoryUtility.INSTANCE.isActive();
		req.setAttribute("active", Boolean.valueOf(historyOn));
		req.setAttribute("listsize", ThreadHistoryUtility.INSTANCE.getMaxEventsSize());
		
		if (!historyOn)
			return mapping.success();
		
		req.setAttribute("history", ThreadHistoryUtility.INSTANCE.getThreadHistoryEvents());
		
		return mapping.success();
	}

}
