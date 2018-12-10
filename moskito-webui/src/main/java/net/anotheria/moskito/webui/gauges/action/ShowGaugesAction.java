package net.anotheria.moskito.webui.gauges.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.gauges.bean.GaugeBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.15 21:38
 */
public class ShowGaugesAction extends BaseGaugesAction{

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		httpServletRequest.setAttribute("gauges", getGaugeBeans(getGaugeAPI().getGauges()));
		return actionMapping.success();
	}

	//temporarly put it under more
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_GAUGES;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

	private List<GaugeBean> getGaugeBeans(List<GaugeAO> gaugeAOList) throws APIException {
		List<GaugeBean> ret = new ArrayList<>();
		if (gaugeAOList == null || gaugeAOList.size() == 0)
			return ret;

		for (GaugeAO gaugeAO : gaugeAOList) {
			String dashboardNames = getDashboardAPI().getDashboardNamesWhichDoNotIncludeThisGauge(gaugeAO.getName());
			ret.add(new GaugeBean(gaugeAO, dashboardNames));
		}

		return ret;
	}


	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskGauges?ts="+System.currentTimeMillis();
	}
}
