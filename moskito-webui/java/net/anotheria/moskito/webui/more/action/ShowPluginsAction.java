package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.api.PluginAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.03.13 18:19
 */
public class ShowPluginsAction extends BaseAdditionalAction{
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
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
