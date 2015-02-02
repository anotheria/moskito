package net.anotheria.moskito.webui.threshold.action;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;

/**
 * Base class for thresholds.
 *
 * @author lrosenberg
 * @created 23.08.12 09:51
 */
public abstract class BaseThresholdsAction extends BaseMoskitoUIAction {

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.THRESHOLDS;
	}
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	protected ThresholdAPI getThresholdAPI(){
		return APILookupUtility.getThresholdAPI();
	}

	@Override
	protected String getSubTitle() {
		return "Thresholds";
	}

}
