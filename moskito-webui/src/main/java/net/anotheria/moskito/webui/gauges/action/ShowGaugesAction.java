package net.anotheria.moskito.webui.gauges.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.15 21:38
 */
public class ShowGaugesAction extends BaseGaugesAction{

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		httpServletRequest.setAttribute("gauges", getGaugeAPI().getGauges());
		return actionMapping.success();
	}

	//temporarly put it under more
	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_GAUGE;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.MORE;
	}

}
