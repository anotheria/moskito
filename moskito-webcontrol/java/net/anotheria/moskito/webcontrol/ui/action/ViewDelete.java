package net.anotheria.moskito.webcontrol.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webcontrol.configuration.ConfigurationRepository;

public class ViewDelete extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		if (request.getParameter("viewName")!=null){
			ConfigurationRepository.INSTANCE.removeView(ConfigurationRepository.INSTANCE.getView(request.getParameter("viewName")));
		}
		
		
		return mapping.findForward("success");
	}

}
