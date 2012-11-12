package net.anotheria.moskito.webcontrol.ui.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webcontrol.configuration.ConfigurationRepository;
import net.anotheria.moskito.webcontrol.configuration.ViewConfiguration;
import net.anotheria.moskito.webcontrol.configuration.ViewField;
import net.anotheria.util.StringUtils;

public class ViewConfigAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if (!StringUtils.isEmpty(action) && "save".equalsIgnoreCase(action)) {
			JSONObject views = new JSONObject("{" + request.getParameter("json") + "}");
			ConfigurationRepository.INSTANCE.updateViews(views);
		}
		
		System.out.println("********************************");
		
		List<ViewField> columns = ConfigurationRepository.INSTANCE.getAvailableColumns();
		List<ViewConfiguration> views = ConfigurationRepository.INSTANCE.getAllViews();
		request.setAttribute("availableColumns", columns);
		request.setAttribute("views", views);
		return mapping.findForward("success");
	}

}
