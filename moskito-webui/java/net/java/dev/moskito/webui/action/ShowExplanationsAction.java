package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;
import net.java.dev.moskito.webui.bean.DecoratorExplanationBean;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.decorators.DecoratorRegistryFactory;
import net.java.dev.moskito.webui.decorators.IDecorator;

/**
 * Forwards to the explanation rendering page. The explanation page renders explanations for the 
 * different values monitored and presented by moskito.
 * @author lrosenberg
 */
public class ShowExplanationsAction extends BaseMoskitoUIAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return req.getRequestURI();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		List<DecoratorExplanationBean> beans = new ArrayList<DecoratorExplanationBean>();
		List<IDecorator> decorators = DecoratorRegistryFactory.getDecoratorRegistry().getDecorators();
		decorators = StaticQuickSorter.sort(decorators, new DummySortType());
		for (IDecorator d : decorators){
			DecoratorExplanationBean bean = new DecoratorExplanationBean();
			bean.setName(d.getName());
			bean.setCaptions(d.getCaptions());
			beans.add(bean);
		}
		
		req.setAttribute("decorators", beans);
		return mapping.success();
	}
	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.NONE;
	}

}
