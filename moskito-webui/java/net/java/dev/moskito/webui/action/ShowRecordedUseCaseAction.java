package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMapping;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.webui.bean.RecordedUseCaseBean;
import net.java.dev.moskito.webui.bean.UseCasePathElementBean;

public class ShowRecordedUseCaseAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowRecordedUseCase?pUseCaseName="+req.getParameter("pUseCaseName");
	}

	@Override
	public ActionForward execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String useCaseName = req.getParameter("pUseCaseName");
		ExistingRunningUseCase useCase = getUseCaseRecorder().getRecordedUseCaseByName(useCaseName);
		
		PathElement root = useCase.getRootElement();
		RecordedUseCaseBean bean = new RecordedUseCaseBean();
		bean.setName(useCase.getName());
		bean.setCreated(useCase.getCreated());
		bean.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			
		ArrayList<UseCasePathElementBean> elements = new ArrayList<UseCasePathElementBean>();
		fillUseCasePathElementBeanList(elements, root,0, getCurrentUnit(req).getUnit());
		bean.setElements(elements);
		
		req.setAttribute("recordedUseCase", bean);
		
		return mapping.findForward("success");
	}
	
	private void fillUseCasePathElementBeanList(List<UseCasePathElementBean> list, PathElement element, int recursion, TimeUnit unit){
		UseCasePathElementBean b = new UseCasePathElementBean();
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
		for (PathElement p : element.getChildren()){
			
			timespentInChilds += p.getDuration();
			fillUseCasePathElementBeanList(list, p, recursion+1, unit);
		}
		b.setTimespent(unit.transformNanos(element.getDuration() - timespentInChilds));
		
	}

}
