package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CssAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.findForward("css");
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}
	
}
