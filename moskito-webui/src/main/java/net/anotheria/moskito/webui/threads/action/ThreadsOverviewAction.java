package net.anotheria.moskito.webui.threads.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.threads.api.ThreadsInfoAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action renders the overview page of the threads section of the webui.
 */
public class ThreadsOverviewAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		ThreadsInfoAO infoBean = getThreadAPI().getThreadsInfo();
		req.setAttribute("info", infoBean);
		
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "threads_overview";
	}


}
