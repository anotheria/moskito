package net.java.dev.moskito.webui.action.threads;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

public class ThreadsOverviewAction extends BaseThreadsAction{

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.success();
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}


}
