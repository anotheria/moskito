package net.java.dev.moskito.webui.action.thresholds;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.treshold.AlertHistory;
import net.java.dev.moskito.core.treshold.Threshold;
import net.java.dev.moskito.core.treshold.ThresholdAlert;
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdRepository;
import net.java.dev.moskito.webui.action.BaseMoskitoUIAction;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.ThresholdAlertBean;
import net.java.dev.moskito.webui.bean.ThresholdBean;
import net.java.dev.moskito.webui.bean.ThresholdInfoBean;
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
		
		ArrayList<ThresholdAlertBean> aBeans = new ArrayList<ThresholdAlertBean>();
		for (ThresholdAlert alert : AlertHistory.INSTANCE.getAlerts()){
			ThresholdAlertBean alertBean = new ThresholdAlertBean();
			alertBean.setId(alert.getThreshold().getInstanceNumber());
			alertBean.setName(alert.getThreshold().getName());
			alertBean.setOldColorCode(alert.getOldStatus().toString().toLowerCase());
			alertBean.setOldStatus(alert.getOldStatus().toString());
			alertBean.setOldValue(alert.getOldValue());
			alertBean.setNewColorCode(alert.getNewStatus().toString().toLowerCase());
			alertBean.setNewStatus(alert.getNewStatus().toString());
			alertBean.setNewValue(alert.getNewValue());
			alertBean.setTimestamp(NumberUtils.makeISO8601TimestampString(alert.getTimestamp()));
			aBeans.add(alertBean);
		}

		
		req.setAttribute("thresholds", tBeans);
		req.setAttribute("infos", iBeans);
		req.setAttribute("alerts", aBeans);
		
		return mapping.findCommand(getForward(req));
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskThresholds?ts="+System.currentTimeMillis();
	}


}
