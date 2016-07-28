package net.anotheria.moskito.webui.shared.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.webui.shared.bean.DecoratorExplanationBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

		List<IDecorator> decorators = DecoratorRegistryFactory.getDecoratorRegistry().getDecorators();
		decorators = StaticQuickSorter.sort(decorators, new DummySortType());
		Collection<DecoratorExplanationBean> beans = new ArrayList<>(decorators.size());
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
	@Override
	protected String getPageName() {
		return "explanations";
	}


}
