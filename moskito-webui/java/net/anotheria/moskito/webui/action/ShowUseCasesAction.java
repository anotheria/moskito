package net.anotheria.moskito.webui.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.webui.bean.NaviItem;
import net.anotheria.moskito.webui.bean.TracedCallListItemBean;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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
