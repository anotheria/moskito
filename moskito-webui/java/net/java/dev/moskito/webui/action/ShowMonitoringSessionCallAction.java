package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionForward;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.core.usecase.running.ExistingRunningUseCase;
import net.java.dev.moskito.core.usecase.running.PathElement;
import net.java.dev.moskito.core.usecase.session.MonitoringSession;
import net.java.dev.moskito.core.usecase.session.MonitoringSessionManagerFactory;
import net.java.dev.moskito.webui.bean.RecordedUseCaseBean;
import net.java.dev.moskito.webui.bean.UseCaseElementNodeBean;
import net.java.dev.moskito.webui.bean.UseCasePathElementBean;

public class ShowMonitoringSessionCallAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowMonitoringSessionCall?pSessionName="+req.getParameter("pSessionName")+"&pPos="+req.getParameter("pPos");
	}

	@Override
	public ActionForward execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String sessionName = req.getParameter("pSessionName");
		int useCasePosition = 0;
		try{
			useCasePosition = Integer.parseInt(req.getParameter("pPos"));
		}catch(Exception ignored){}
				
		MonitoringSession currentSession = MonitoringSessionManagerFactory.getMonitoringSessionManager().getSession(sessionName);
		ExistingRunningUseCase useCase = currentSession.getUseCases().get(useCasePosition);
		
		PathElement root = useCase.getRootElement();
		RecordedUseCaseBean bean = new RecordedUseCaseBean();
		bean.setName(useCase.getName());
		bean.setCreated(useCase.getCreated());
		bean.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			
		TimeUnit unit = getCurrentUnit(req).getUnit();
		
		ArrayList<UseCasePathElementBean> elements = new ArrayList<UseCasePathElementBean>();
		fillUseCasePathElementBeanList(elements, root,0, unit);
		bean.setElements(elements);
		
		req.setAttribute("recordedUseCase", bean);
		
		//prepare tree view
		List<PathElement> topElements = root.getChildren();
		List<UseCaseElementNodeBean> nodes = new ArrayList<UseCaseElementNodeBean>();
		for (PathElement topElement: topElements){
			nodes.add(createNodeBean(topElement, unit));
		}
		req.setAttribute("nodes", nodes);
		
		return mapping.findForward("success");
	}
	
	private UseCaseElementNodeBean createNodeBean(PathElement element, TimeUnit unit){
		UseCaseElementNodeBean ret = new UseCaseElementNodeBean(element.getCall(), unit.transformNanos(element.getDuration()), element.isAborted() );
		
		long timeInChildren = 0;
		for (PathElement elem : element.getChildren()){
			UseCaseElementNodeBean child = createNodeBean(elem, unit);
			timeInChildren += child.getDuration();
			ret.addChild(child);
		}
		
		ret.setTimespent(unit.transformNanos(element.getDuration()-timeInChildren));
		
		
		return ret;
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
		b.setTimespent(unit.transformNanos((element.getDuration() - timespentInChilds)));
		
	}

}
