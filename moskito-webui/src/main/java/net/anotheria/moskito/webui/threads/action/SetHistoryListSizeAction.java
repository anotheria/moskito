package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.util.threadhistory.ThreadHistoryUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets the size of the history array and therefore history length.
 */
public class SetHistoryListSizeAction extends ThreadsHistoryAction{

	@Override
	public ActionCommand execute(ActionMapping mapping,HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThreadHistoryUtility.INSTANCE.setMaxEventsSize(Integer.parseInt(req.getParameter("pSize")));
		
		return super.execute(mapping, req, res);
	}
	
}
