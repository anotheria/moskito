package net.anotheria.moskito.webui.action.thresholds;

import net.anotheria.moskito.core.treshold.guard.GuardedDirection;
import net.anotheria.moskito.webui.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.bean.NaviItem;

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

	protected boolean hasDots(String ... params){
		if (params==null)
			return false;
		for (String p : params){
			if (p!=null && p.indexOf('.')>0)
				return true;
		}
		return false;
	}

	protected GuardedDirection string2direction(String param){
		if (param.equalsIgnoreCase("below"))
			return GuardedDirection.DOWN;
		if (param.equalsIgnoreCase("above"))
			return GuardedDirection.UP;
		throw new IllegalArgumentException("Unknown parameter value for direction "+param+", expected below or above.");
	}


}
