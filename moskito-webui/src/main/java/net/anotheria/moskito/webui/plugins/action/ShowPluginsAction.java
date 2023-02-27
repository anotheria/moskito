package net.anotheria.moskito.webui.plugins.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.api.PluginAO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 18:19
 */
public class ShowPluginsAction extends BasePluginAction {
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskPlugins";
	}

	@Override
	public ActionCommand execute(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		List<PluginAO> plugins = getAdditionalFunctionalityAPI().getPlugins();
		httpServletRequest.setAttribute("plugins", plugins);
		httpServletRequest.setAttribute("pluginsCount", plugins.size());


		return actionMapping.success();
	}

	@Override
	protected String getPageName() {
		return "plugins";
	}



}
