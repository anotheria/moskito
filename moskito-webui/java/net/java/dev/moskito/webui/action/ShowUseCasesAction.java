package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.TracedCallListItemBean;

public class ShowUseCasesAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) {
		
		//prepare RecordedUseCases
		List<CurrentlyTracedCall> recorded = getUseCaseRecorder().getRecordedUseCases();
		List<TracedCallListItemBean> beans = new ArrayList<TracedCallListItemBean>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			CurrentlyTracedCall useCase = recorded.get(i);
			TracedCallListItemBean b = new TracedCallListItemBean();
			b.setName(useCase.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			beans.add(b);
		}
		
		req.setAttribute("recorded", beans);
		if (beans.size()>0)
			req.setAttribute("recordedAvailableFlag", Boolean.TRUE);
		return mapping.success();
	}
	
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.USECASES;
	}


}
