package net.java.dev.moskito.webcontrol.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;

public class ShowViewAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		List<String> viewnames = ConfigurationRepository.INSTANCE.getViewNames();

		String viewname = req.getParameter(REQUEST_PARAM_VIEW_NAME);
		if (viewname == null && viewnames.size() > 0) {
			viewname = viewnames.get(0);
		}
		
		String interval = req.getParameter(REQUEST_PARAM_INTERVAL);
		if (StringUtils.isEmpty(interval)) {
			interval = "default";
		}

		req.setAttribute("viewnames", viewnames);
		req.setAttribute("view", prepareView(viewname, interval));
		req.setAttribute("intervalNames", ConfigurationRepository.INSTANCE.getIntervalsNames());

		return mapping.findForward("success");
	}

}
