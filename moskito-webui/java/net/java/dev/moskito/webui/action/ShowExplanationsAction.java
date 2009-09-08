package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.QuickSorter;
import net.anotheria.util.sorter.Sorter;
import net.java.dev.moskito.webui.bean.DecoratorExplanationBean;
import net.java.dev.moskito.webui.decorators.DecoratorRegistryFactory;
import net.java.dev.moskito.webui.decorators.IDecorator;

public class ShowExplanationsAction extends BaseMoskitoUIAction{

	private Sorter<IDecorator> sorter = new QuickSorter<IDecorator>();
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return req.getRequestURI();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		List<DecoratorExplanationBean> beans = new ArrayList<DecoratorExplanationBean>();
		List<IDecorator> decorators = DecoratorRegistryFactory.getDecoratorRegistry().getDecorators();
		decorators = sorter.sort(decorators, new DummySortType());
		for (IDecorator d : decorators){
			DecoratorExplanationBean bean = new DecoratorExplanationBean();
			bean.setName(d.getName());
			bean.setCaptions(d.getCaptions());
			beans.add(bean);
		}
		
		req.setAttribute("decorators", beans);
		return mapping.findForward("success");
	}

}
