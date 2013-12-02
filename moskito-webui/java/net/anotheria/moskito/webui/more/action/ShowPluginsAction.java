package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.config.plugins.PluginConfig;
import net.anotheria.moskito.core.plugins.PluginRepository;
import net.anotheria.moskito.webui.more.bean.PluginBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

		List<String> pluginNames = PluginRepository.getInstance().getPluginNames();
		ArrayList<PluginBean> beans = new ArrayList<PluginBean>();
		for (String s : pluginNames){
			PluginBean bean = new PluginBean();

			bean.setName(s);
			try{
				bean.setDescription(""+PluginRepository.getInstance().getPlugin(s));
			}catch(Exception e){
				bean.setDescription("Error: "+e.getMessage());
			}

			PluginConfig config = PluginRepository.getInstance().getConfig(s);
			if (config==null){
				bean.setClassName("-not found-");
				bean.setConfigurationName("-not found-");
			}else{
				bean.setConfigurationName(config.getConfigurationName());
				bean.setClassName(config.getClassName());
			}
			beans.add(bean);
		}

		httpServletRequest.setAttribute("plugins", beans);
		httpServletRequest.setAttribute("pluginsCount", beans.size());
		return actionMapping.success();
	}

	@Override
	protected String getPageName() {
		return "plugins";
	}

}
