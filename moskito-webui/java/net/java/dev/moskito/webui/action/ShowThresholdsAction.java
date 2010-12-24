package net.java.dev.moskito.webui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.webui.bean.NaviItem;

public class ShowThresholdsAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		ThresholdRepository.INSTANCE.getThresholds();
		
		return mapping.findForward("success");
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskThresholds?ts="+System.currentTimeMillis();
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.THRESHOLDS;
	}

}
