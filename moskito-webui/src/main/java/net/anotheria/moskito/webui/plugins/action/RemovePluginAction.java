package net.anotheria.moskito.webui.plugins.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Removes a plugin with given name from plugin repository.
 *
 * @author lrosenberg
 * @since 20.03.13 00:10
 */
public class RemovePluginAction extends BasePluginAction {
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		String pluginName = req.getParameter("pPluginName");
		getAdditionalFunctionalityAPI().removePlugin(pluginName);
		return mapping.redirect();
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}
}
