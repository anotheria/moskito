package net.java.dev.moskito.webcontrol.ui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMapping;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.ui.beans.ViewTable;

public class ShowAllViewsAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		
		List<String> viewnames = ConfigurationRepository.INSTANCE.getViewNames();
		List<ViewTable> views = new ArrayList<ViewTable>(viewnames.size());
		
		for (String viewName : viewnames) {
			views.add(prepareView(viewName));
		}
		
		req.setAttribute("views", views);
		
		return mapping.findForward("success");
	}

}
