package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webui.bean.NaviItem;

/**
 * This action simply forwards to the css page. It is useful to pass through css filters in some configurations.
 * @author lrosenberg.
 *
 */
public class CssAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.findForward("css");
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.NONE;
	}
	
}
