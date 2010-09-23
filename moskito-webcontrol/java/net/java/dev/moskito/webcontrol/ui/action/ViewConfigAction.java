package net.java.dev.moskito.webcontrol.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.configuration.ViewConfiguration;
import net.java.dev.moskito.webcontrol.configuration.ViewField;

public class ViewConfigAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if (!StringUtils.isEmpty(action) && "save".equalsIgnoreCase(action)) {
			JSONObject views = new JSONObject("{" + request.getParameter("json") + "}");
			ConfigurationRepository.INSTANCE.updateViews(views);
		}
		
		List<ViewField> columns = ConfigurationRepository.INSTANCE.getAvailableColumns();
		List<ViewConfiguration> views = ConfigurationRepository.INSTANCE.getAllViews();
		request.setAttribute("availableColumns", columns);
		request.setAttribute("views", views);
		return mapping.findForward("success");
	}

}
