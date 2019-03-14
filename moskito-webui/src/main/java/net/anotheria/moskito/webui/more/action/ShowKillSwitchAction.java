package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.KillSwitchConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (!APILookupUtility.isLocal()) {
			MoskitoConfiguration config = getAdditionalFunctionalityAPI().getConfiguration();
			KillSwitchConfiguration killSwitchConfiguration = config.getKillSwitch();

			req.setAttribute("killSwitchConfiguration", killSwitchConfiguration != null ? killSwitchConfiguration : new KillSwitchConfiguration());
		}
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
