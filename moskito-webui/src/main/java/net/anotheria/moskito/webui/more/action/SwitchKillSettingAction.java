package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.json.JSONResponse;
import net.anotheria.moskito.core.config.KillSwitchConfiguration;
import net.anotheria.moskito.webui.shared.action.BaseAJAXMoskitoUIAction;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.util.APILookupUtility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * This action allows to change SwitchKill setting values.
 */
public class SwitchKillSettingAction extends BaseAJAXMoskitoUIAction {

	@Override
	protected void invokeExecute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res, JSONResponse jsonResponse) throws Exception {
		String name = req.getParameter("name");
		String value = req.getParameter("value");
		boolean val = Boolean.parseBoolean(value);

		KillSwitchConfiguration killSwitchConfiguration = getAdditionalFunctionalityAPI().getConfiguration().getKillSwitch();
		if (killSwitchConfiguration == null)
			killSwitchConfiguration = new KillSwitchConfiguration();

		if ("metricCollection".equals(name))
			killSwitchConfiguration.setDisableMetricCollection(val);
		else if ("tracing".equals(name))
			killSwitchConfiguration.setDisableTracing(val);

		getAdditionalFunctionalityAPI().setKillSwitchConfiguration(killSwitchConfiguration);
	}

	private AdditionalFunctionalityAPI getAdditionalFunctionalityAPI() {
		return APILookupUtility.getAdditionalFunctionalityAPI();
	}
}
