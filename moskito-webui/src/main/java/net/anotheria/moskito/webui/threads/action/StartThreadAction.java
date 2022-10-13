package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Starts a new thread for testing purposes of the thread history.
 */
public class StartThreadAction extends ThreadsHistoryAction{

	@Override
	public ActionCommand execute(ActionMapping mapping,HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		getThreadAPI().startTestThread();
		
		
		return super.execute(mapping, req, res);
	}

}
