package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This action shows current kill switch options.
 *
 */
public class ShowKillSwitchAction extends BaseAdditionalAction{
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskKillSwitch?ts="+System.currentTimeMillis();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "killswitch";
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_KILLSWITCH;
	}

	@Override
	protected String getSubTitle() {
		return "Kill Switch";
	}

}
