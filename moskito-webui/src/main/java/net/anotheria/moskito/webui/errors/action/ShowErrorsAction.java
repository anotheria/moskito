package net.anotheria.moskito.webui.errors.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.api.ErrorCatcherAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.06.17 23:31
 */
public class ShowErrorsAction extends BaseErrorAction {
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskErrors?ts="+System.currentTimeMillis();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<ErrorCatcherAO> catchers = getAdditionalFunctionalityAPI().getActiveErrorCatchers();
		if (catchers!=null && catchers.size()>0)
			req.setAttribute("catchers", catchers);

		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "errors";
	}

	@Override
	protected String getSubTitle() {
		return "Errors";
	}
}
