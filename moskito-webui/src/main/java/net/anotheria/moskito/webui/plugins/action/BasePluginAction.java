package net.anotheria.moskito.webui.plugins.action;

import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.shared.api.PluginAO;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.util.APILookupUtility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Deque;
import java.util.LinkedList;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.10.12 23:41
 */
public abstract class BasePluginAction extends BaseMoskitoUIAction{
	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.PLUGINS;
	}

	protected AdditionalFunctionalityAPI getAdditionalFunctionalityAPI(){
		return APILookupUtility.getAdditionalFunctionalityAPI();
	}

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		super.preProcess(mapping, req, res);
		Deque<PluginAO> pluginsForNavi = new LinkedList<>();
		for (PluginAO pluginAO : getAdditionalFunctionalityAPI().getPlugins()){
			if (pluginAO.isWebEnabled()) {
				pluginsForNavi.add(pluginAO);
				if (pluginAO.getNavigationEntryAction().equals(mapping.getPath())) {
					pluginAO.setWebSelected(true);
				}
			}
		}

		req.setAttribute("pluginsForNavi", pluginsForNavi);
	}
}
