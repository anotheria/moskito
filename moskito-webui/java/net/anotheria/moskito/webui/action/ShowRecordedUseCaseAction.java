package net.anotheria.moskito.webui.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.bean.NaviItem;
import net.anotheria.moskito.webui.bean.TraceStepBean;
import net.anotheria.moskito.webui.bean.TracedCallBean;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class ShowRecordedUseCaseAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowRecordedUseCase?pUseCaseName="+req.getParameter("pUseCaseName");
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String useCaseName = req.getParameter("pUseCaseName");
		CurrentlyTracedCall useCase = getUseCaseRecorder().getRecordedUseCaseByName(useCaseName);
		
		TraceStep root = useCase.getRootStep();
		TracedCallBean bean = new TracedCallBean();
		bean.setName(useCase.getName());
		bean.setCreated(useCase.getCreated());
		bean.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			
		ArrayList<TraceStepBean> elements = new ArrayList<TraceStepBean>();
		fillUseCasePathElementBeanList(elements, root,0, getCurrentUnit(req).getUnit());
		bean.setElements(elements);
		
		req.setAttribute("recordedUseCase", bean);
		
		return mapping.success();
	}
	
	private void fillUseCasePathElementBeanList(List<TraceStepBean> list, TraceStep element, int recursion, TimeUnit unit){
		TraceStepBean b = new TraceStepBean();
		b.setCall(recursion == 0 ? "ROOT" : element.getCall());
		b.setRoot(recursion == 0);
		b.setLayer(recursion);

		b.setDuration(unit.transformNanos(element.getDuration()));
		String ident = "";
		for (int i=0; i<recursion; i++)
			ident += "&nbsp;&nbsp;";
		b.setIdent(ident);
		b.setAborted(element.isAborted());
		list.add(b);

		long timespentInChilds = 0;
		for (TraceStep p : element.getChildren()){
			
			timespentInChilds += p.getDuration();
			fillUseCasePathElementBeanList(list, p, recursion+1, unit);
		}
		b.setTimespent(unit.transformNanos(element.getDuration() - timespentInChilds));
		
	}
	
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.USECASES;
	}


}
