/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.anotheria.moskito.webui.producers.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.stats.UnknownIntervalException;
import net.anotheria.moskito.webui.decorators.IDecorator;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.ProducerAOSortType;
import net.anotheria.moskito.webui.producers.api.StatValueAO;
import net.anotheria.moskito.webui.producers.api.UnitCountAO;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean;
import net.anotheria.util.sorter.StaticQuickSorter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base action for producers presentation action.
 * @author lrosenberg.
 */
public abstract class BaseShowProducersAction extends BaseMoskitoUIAction {

	/**
	 * Returns the list of producers for presentation.
	 * @param req
	 * @return
	 */
	protected abstract List<ProducerAO> getProducers(HttpServletRequest req);
	/**
	 * Returns the page title. 
	 * @param req
	 * @return
	 */
	public abstract String getPageTitle(HttpServletRequest req);
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		Map<String, GraphDataBean> graphData = new HashMap<String, GraphDataBean>();

		req.setAttribute("decorators", getDecoratedProducers(req, getProducers(req), graphData));
		req.setAttribute("graphDatas", graphData.values());

		doCustomProcessing(req, res);
		
		req.setAttribute("pageTitle", getPageTitle(req));

		if (getForward(req).equalsIgnoreCase("csv")){
			res.setHeader("Content-Disposition", "attachment; filename=\"producers.csv\"");
		}

		return mapping.findCommand( getForward(req) );
	}

	private static final UnitCountAO EMPTY_UNIT = new UnitCountAO("Select ", 0);

	protected void doCustomProcessing(HttpServletRequest req, HttpServletResponse res){
		try{
			List<UnitCountAO> categories = getProducerAPI().getCategories();
			categories.add(0, EMPTY_UNIT);
			req.setAttribute("categories", categories);

			List<UnitCountAO> subsystems = getProducerAPI().getSubsystems();
			subsystems.add(0, EMPTY_UNIT);
			req.setAttribute("subsystems", subsystems);
		}catch(APIException e){
			throw new IllegalStateException("Couldn't obtain categories/subsystems ", e);
		}
	}

	@Override
	protected final NaviItem getCurrentNaviItem() {
		return NaviItem.PRODUCERS;
	}

	//todo make separate method for graphData in future
	protected List<ProducerDecoratorBean> getDecoratedProducers(HttpServletRequest req, List<ProducerAO> producers, Map<String, GraphDataBean> graphData){

		Map<IDecorator, List<ProducerAO>> decoratorMap = new HashMap<IDecorator,List<ProducerAO>>();
		for (ProducerAO producer : producers){
			try{
				IDecorator decorator = getDecoratorRegistry().getDecorator(producer.getStatsClazz());
				if (!decoratorMap.containsKey(decorator)){
					decoratorMap.put(decorator, new ArrayList<ProducerAO>());

					for(StatValueAO statBean : producer.getFirstStatsValues()){
						String graphKey = decorator.getName()+"_"+statBean.getName();
						GraphDataBean graphDataBean = new GraphDataBean(decorator.getName()+"_"+statBean.getJsVariableName(), statBean.getName());
						graphData.put(graphKey, graphDataBean);
					}
				}
				decoratorMap.get(decorator).add(producer);
			}catch(IndexOutOfBoundsException e){
				//producer has no stats at all, ignoring
			}
		}


		List<ProducerDecoratorBean> beans = new ArrayList<ProducerDecoratorBean>();

		for (IDecorator decorator : decoratorMap.keySet()){
			ProducerDecoratorBean b = new ProducerDecoratorBean();
			b.setName(decorator.getName());
			b.setCaptions(decorator.getCaptions());

			for (ProducerAO p : decoratorMap.get(decorator)){
				try {
					List<StatValueAO> values = p.getFirstStatsValues();
					for (StatValueAO valueBean : values){
						String graphKey = decorator.getName()+"_"+valueBean.getName();
						GraphDataBean bean = graphData.get(graphKey); 
						if (bean==null) {
						    // FIXME!
						    System.out.println("unable to find bean for key: " + graphKey);
						} else {
						    bean.addValue(new GraphDataValueBean(p.getProducerId(), valueBean.getRawValue()));
						}
					}
				}catch(UnknownIntervalException e){
					//do nothing, apparently we have a decorator which has no interval support for THIS interval.
				}
			}
			b.setProducerBeans(StaticQuickSorter.sort(decoratorMap.get(decorator), getProducerBeanSortType(b, req)));
			b.setVisibility(getProducerVisibility(b, req));
			beans.add(b);
		}

		return beans;
	}

	private ProducerVisibility getProducerVisibility(ProducerDecoratorBean decoratorBean, HttpServletRequest req){

		ProducerVisibility visibility;

		String paramVisibility = req.getParameter(decoratorBean.getProducerVisibilityParameterName());
		if (paramVisibility != null && paramVisibility.length() > 0){
			visibility = ProducerVisibility.fromString(paramVisibility);
			req.getSession().setAttribute(decoratorBean.getProducerVisibilityBeanName(), visibility);
			return visibility;
		}

		visibility = (ProducerVisibility)req.getSession().getAttribute(decoratorBean.getProducerVisibilityBeanName());

		if (visibility == null) {
			visibility = ProducerVisibility.SHOW;
			req.getSession().setAttribute(decoratorBean.getProducerVisibilityBeanName(), visibility);
		}

		return visibility;
	}

	private ProducerAOSortType getProducerBeanSortType(ProducerDecoratorBean decoratorBean, HttpServletRequest req){
		ProducerAOSortType sortType;
		String paramSortBy = req.getParameter(decoratorBean.getSortByParameterName());
		if (paramSortBy!=null && paramSortBy.length()>0){
			try{
				int sortBy = Integer.parseInt(paramSortBy);
				String paramSortOrder = req.getParameter(decoratorBean.getSortOrderParameterName());
				boolean sortOrder = paramSortOrder!=null && paramSortOrder.equals("ASC") ?
						ProducerAOSortType.ASC : ProducerAOSortType.DESC;
				sortType = new ProducerAOSortType(sortBy, sortOrder);
				req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
				return sortType;
			}catch(NumberFormatException skip){}
		}
		sortType = (ProducerAOSortType)req.getSession().getAttribute(decoratorBean.getSortTypeName());
		if (sortType==null){
			sortType = new ProducerAOSortType();
			req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
		}
		return sortType;
	}

	@Override
	protected String getPageName() {
		return "producers";
	}



}
