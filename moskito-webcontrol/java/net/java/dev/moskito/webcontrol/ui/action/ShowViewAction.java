package net.java.dev.moskito.webcontrol.ui.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.webcontrol.configuration.ConfigurationRepository;
import net.java.dev.moskito.webcontrol.ui.beans.ColumnBean;
import net.java.dev.moskito.webcontrol.ui.beans.ComparableOrderedSource;
import net.java.dev.moskito.webcontrol.ui.beans.ViewTable;

public class ShowViewAction extends BaseMoskitoWebcontrolAction {

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {
		List<String> viewnames = ConfigurationRepository.INSTANCE.getViewNames();

		String sortBy = req.getParameter(REQUEST_PARAM_SORT_BY);
		String sortOrder = req.getParameter(REQUEST_PARAM_SORT_ORDER);

		String viewname = req.getParameter(REQUEST_PARAM_VIEW_NAME);
		if (viewname == null && viewnames.size() > 0) {
			viewname = viewnames.get(0);
		}

		String interval = req.getParameter(REQUEST_PARAM_INTERVAL);
		if (StringUtils.isEmpty(interval)) {
			interval = "default";
		}

		req.setAttribute("viewnames", viewnames);

		ViewTable viewTable = prepareView(viewname, interval);

		if (sortBy != null) {
			sorting(viewTable, sortBy, sortOrder);
		}

		req.setAttribute("view", viewTable);

		req.setAttribute("interval", interval);
		req.setAttribute("intervalNames", ConfigurationRepository.INSTANCE.getIntervalsNames());
		req.setAttribute("sortBy", sortBy);
		req.setAttribute("sortOrder", sortOrder);

		return mapping.findForward("success");
	}

	private void sorting(ViewTable viewTable, String sortBy, String sortOrder) {
		int attrNumber = getAttributeNumber(viewTable.getRowNames(), sortBy);
		Collections.sort(viewTable.getValues(), new ComparableOrderedSource(attrNumber, sortOrder.equalsIgnoreCase("desc") ? -1 : 1));
	}

	private int getAttributeNumber(List<ColumnBean> rowNames, String sortBy) {
		for (int i = 0; i < rowNames.size(); i++) {
			ColumnBean field = rowNames.get(i);
			if (field.getKey().equalsIgnoreCase(sortBy)) {
				return i;
			}
		}
		throw new RuntimeException("SortBy attribute `" + sortBy + "` does not exist in list of view fields.");
	}

}
