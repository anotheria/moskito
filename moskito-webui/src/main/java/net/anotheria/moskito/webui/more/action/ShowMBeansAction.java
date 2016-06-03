package net.anotheria.moskito.webui.more.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.shared.api.MBeanWrapperAO;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This action displays all mbeans in the jvm.
 *
 * @author lrosenberg
 * @since 11.03.13 00:57
 */
public class ShowMBeansAction extends AdditionalSectionAction{
    
	/**
	 * @{inheritDoc}
	 */
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<MBeanWrapperAO> beans = getAdditionalFunctionalityAPI().getMBeans();
		req.setAttribute("mbeansCount", beans.size());
		req.setAttribute("mbeans", beans);
		return mapping.success();
	}

	@Override
	protected NaviItem getCurrentSubNaviItem() {
		return NaviItem.MORE_MBEANS;
	}

}
