package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.ThresholdBean;

public class ShowThresholdsAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Threshold> thresholds = ThresholdRepository.INSTANCE.getThresholds();
		ArrayList<ThresholdBean> tBeans = new ArrayList<ThresholdBean>();
		
		for (Threshold t : thresholds){
			ThresholdBean bean = new ThresholdBean();
			
			bean.setName(t.getName());
			bean.setColorCode(t.getStatus().toString().toLowerCase());
			bean.setStatus(t.getStatus().toString().toLowerCase());
			bean.setDescription(t.getDefinition().describe());
			bean.setTimestamp(t.getStatusChangeTimestamp() == 0 ? "Never" : NumberUtils.makeISO8601TimestampString(t.getStatusChangeTimestamp()));
			bean.setValue(t.getLastValue());
			bean.setChange(t.getStatusChange());
			
			
			tBeans.add(bean);
		}
		
		System.out.println("creating thresholds out of thresholds "+thresholds+" -> "+tBeans);
		
		req.setAttribute("thresholds", tBeans);
		
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
