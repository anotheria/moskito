package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action returns simply forward to the "everything else" navigation point.
 *
 * @author lrosenberg
 * @since 29.10.12 00:21
 */
public class AdditionalSectionAction extends BaseAdditionalAction{
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskMore?ts="+System.currentTimeMillis();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "more";
	}

}
