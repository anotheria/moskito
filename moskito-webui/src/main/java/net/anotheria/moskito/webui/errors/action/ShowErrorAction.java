package net.anotheria.moskito.webui.errors.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.api.CaughtErrorAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Shows all instances of a specific error/exception class.
 *
 * @author lrosenberg
 * @since 09.06.17 15:15
 */
public class ShowErrorAction extends BaseErrorAction {

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskError?error=" + req.getParameter("error");
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String catcherName = req.getParameter("name");
		String catcherType = req.getParameter("type");
		List<CaughtErrorAO> caughtErrors = getAdditionalFunctionalityAPI().getCaughtErrorsByExceptionName(catcherName, catcherType);

		req.setAttribute("clazz", catcherName);
		req.setAttribute("caughtErrors", caughtErrors);
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "error";
	}

	@Override
	protected String getSubTitle() {
		return "Errors";
	}
}
