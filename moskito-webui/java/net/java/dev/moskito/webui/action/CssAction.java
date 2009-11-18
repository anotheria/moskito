package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMapping;
/**
 * This action simply forwards to the css page. It is useful to pass through css filters in some configurations.
 * @author lrosenberg.
 *
 */
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
