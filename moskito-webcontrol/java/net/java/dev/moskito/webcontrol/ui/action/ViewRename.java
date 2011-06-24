package net.java.dev.moskito.webcontrol.ui.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;

public class ViewRename extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		if ((request.getParameter("newViewName")!=null)&&(ConfigurationRepository.INSTANCE.getView(request.getParameter("oldViewName"))!=null)&&(request.getParameter("oldViewName")!=null)){
			ViewConfiguration vc = ConfigurationRepository.INSTANCE.getView(request.getParameter("oldViewName"));
			ConfigurationRepository.INSTANCE.removeView(vc);
			vc.setName(request.getParameter("newViewName"));
			ConfigurationRepository.INSTANCE.addView(vc);
		}
		
		
		return mapping.findForward("success");
	}

}
