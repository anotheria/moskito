package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.KillSwitchConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This action creates a new accumulator.
 */
public class SwitchKillSettingAction extends BaseAdditionalAction {

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskKillSwitch?ts="+System.currentTimeMillis();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		boolean val = Boolean.parseBoolean(value);

		KillSwitchConfiguration killSwitchConfiguration = getAdditionalFunctionalityAPI().getConfiguration().getKillSwitch();
		if (killSwitchConfiguration == null)
			killSwitchConfiguration = new KillSwitchConfiguration();

		if ("disableMetricCollection".equals(name))
			killSwitchConfiguration.setDisableMetricCollection(val);

		getAdditionalFunctionalityAPI().setKillSwitchConfiguration(killSwitchConfiguration);

		return mapping.success();
	}

}
