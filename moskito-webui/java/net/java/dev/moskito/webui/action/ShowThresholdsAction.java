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
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.ThresholdBean;
import net.java.dev.moskito.webui.bean.ThresholdInfoBean;

public class ShowThresholdsAction extends BaseMoskitoUIAction{

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Threshold> thresholds = ThresholdRepository.INSTANCE.getThresholds();
		ArrayList<ThresholdBean> tBeans = new ArrayList<ThresholdBean>();
		ArrayList<ThresholdInfoBean> iBeans = new ArrayList<ThresholdInfoBean>();
		
		for (Threshold t : thresholds){
			ThresholdBean bean = new ThresholdBean();
			
			bean.setName(t.getName());
			bean.setColorCode(t.getStatus().toString().toLowerCase());
			bean.setStatus(t.getStatus().toString().toLowerCase());
			bean.setDescription(t.getDefinition().describe());
			bean.setTimestamp(t.getStatusChangeTimestamp() == 0 ? "Never" : NumberUtils.makeISO8601TimestampString(t.getStatusChangeTimestamp()));
			bean.setValue(t.getLastValue());
			bean.setChange(t.getStatusChange() == null ? "Never" : t.getStatusChange());
			
			bean.setTimestampForSorting(t.getStatusChangeTimestamp());
			bean.setStatusForSorting(t.getStatus());
			bean.setId(t.getInstanceNumber());
			
			tBeans.add(bean);
			
			
			ThresholdInfoBean infoBean = new ThresholdInfoBean();
			infoBean.setId(t.getInstanceNumber());
			infoBean.setName(t.getName());
			infoBean.setProducerName(t.getDefinition().getProducerName());
			infoBean.setStatName(t.getDefinition().getStatName());
			infoBean.setIntervalName(t.getDefinition().getIntervalName());
			infoBean.setValueName(t.getDefinition().getValueName());
			infoBean.setDescriptionString(t.getDefinition().describe());
			for (ThresholdConditionGuard g : t.getGuards()){
				infoBean.addGuard(g.toString());
			}
			iBeans.add(infoBean);
		}
		
		req.setAttribute("thresholds", tBeans);
		req.setAttribute("infos", iBeans);
		
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
