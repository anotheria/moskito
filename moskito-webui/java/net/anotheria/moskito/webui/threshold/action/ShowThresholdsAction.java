package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.bean.ThresholdBean;
import net.anotheria.moskito.webui.threshold.bean.ThresholdInfoBean;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 * Displays configured thresholds and their statuses.
 * @author lrosenberg
 *
 */
public class ShowThresholdsAction extends BaseThresholdsAction {

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		List<Threshold> thresholds = ThresholdRepository.getInstance().getThresholds();
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
			bean.setId(t.getId());
			
			tBeans.add(bean);
			
			
			ThresholdInfoBean infoBean = new ThresholdInfoBean();
			infoBean.setId(t.getId());
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
		req.setAttribute("alerts", getThresholdAPI().getAlerts());
		
		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskThresholds?ts="+System.currentTimeMillis();
	}


}
