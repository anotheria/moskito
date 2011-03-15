package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webui.bean.NaviItem;

public class ShowAccumulatorsAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		return mapping.findForward("success");
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskAccumulators?ts="+System.currentTimeMillis();
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.ACCUMULATORS;
	}
}
