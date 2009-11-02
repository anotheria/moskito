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
package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.ActionForward;
import net.anotheria.maf.ActionMapping;
import net.anotheria.util.sorter.QuickSorter;
import net.anotheria.util.sorter.Sorter;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.webui.bean.MetaHeaderBean;
import net.java.dev.moskito.webui.bean.ProducerBean;
import net.java.dev.moskito.webui.bean.ProducerBeanSortType;
import net.java.dev.moskito.webui.bean.ProducerDecoratorBean;
import net.java.dev.moskito.webui.bean.StatValueBean;
import net.java.dev.moskito.webui.bean.UnitBean;
import net.java.dev.moskito.webui.decorators.IDecorator;

public abstract class BaseShowProducersAction extends BaseMoskitoUIAction{
	
	protected abstract List<IStatsProducer> getProducers(HttpServletRequest req);
	
	public abstract String getPageTitle(HttpServletRequest req);
	
	private Sorter<ProducerBean> sorter;
	
	public BaseShowProducersAction(){
		super();
		sorter = new QuickSorter<ProducerBean>();
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String intervalName = getCurrentInterval(req);
		UnitBean currentUnit = getCurrentUnit(req);

		Map<IDecorator, List<IStatsProducer>> decoratorMap = new HashMap<IDecorator,List<IStatsProducer>>();
		Map<IDecorator, List<MetaHeaderBean>> metaheaderMap = new HashMap<IDecorator, List<MetaHeaderBean>>();
		
		List<IStatsProducer> producers = getProducers(req);
		
		
		
		for (IStatsProducer producer : producers){
			try{
				IStats stats = producer.getStats().get(0);
				IDecorator decorator = getDecoratorRegistry().getDecorator(stats);
				if (!decoratorMap.containsKey(decorator)){
					decoratorMap.put(decorator, new ArrayList<IStatsProducer>());
					
					List<MetaHeaderBean> metaheader = new ArrayList<MetaHeaderBean>();
					for(StatValueBean statBean:decorator.getValues(stats, intervalName, currentUnit.getUnit())){
						MetaHeaderBean bean = new MetaHeaderBean(statBean.getName(),statBean.getType());
						metaheader.add(bean);
					}
					metaheaderMap.put(decorator, metaheader);
				}
				decoratorMap.get(decorator).add(producer);
			}catch(ArrayIndexOutOfBoundsException e){
				//producer has no stats at all, ignoring
			}
		}
		
		//bla bla, now prepare view
		List<IDecorator> decorators = new ArrayList<IDecorator>(decoratorMap.keySet());
		List<ProducerDecoratorBean> beans = new ArrayList<ProducerDecoratorBean>(); 
		//sort
		
		for (IDecorator decorator : decorators){
			ProducerDecoratorBean b = new ProducerDecoratorBean();
			b.setName(decorator.getName());
			b.setCaptions(decorator.getCaptions());
						
			b.setMetaHeader(metaheaderMap.get(decorator));
			
			List<ProducerBean> pbs = new ArrayList<ProducerBean>();
			for (IStatsProducer p : decoratorMap.get(decorator)){
				ProducerBean pb = new ProducerBean();
				pb.setCategory(p.getCategory());
				pb.setClassName(p.getClass().getName());
				pb.setSubsystem(p.getSubsystem());
				pb.setId(p.getProducerId()); 
				IStats firstStats = p.getStats().get(0);
				//System.out.println("Trying "+decorator+", cz: "+decorator.getClass()+", int: "+intervalName+", unit: "+currentUnit.getUnit());
				pb.setValues(decorator.getValues(firstStats, intervalName, currentUnit.getUnit()));
				pbs.add(pb);
			}
			b.setProducerBeans(sorter.sort(pbs, getProducerBeanSortType(b, req)));
			beans.add(b);
		}
		
		req.setAttribute("decorators", beans);
		
		doCustomProcessing(req, res);
		
		req.setAttribute("pageTitle", getPageTitle(req));
		
		return mapping.findForward( getForward(req) );
	}
	
	protected void doCustomProcessing(HttpServletRequest req, HttpServletResponse res){
		//do nothing
	}
	
	
	private ProducerBeanSortType getProducerBeanSortType(ProducerDecoratorBean decoratorBean, HttpServletRequest req){
		ProducerBeanSortType sortType;
		String paramSortBy = req.getParameter(decoratorBean.getSortByParameterName());
		if (paramSortBy!=null && paramSortBy.length()>0){
			try{
				int sortBy = Integer.parseInt(paramSortBy);
				String paramSortOrder = req.getParameter(decoratorBean.getSortOrderParameterName());
				boolean sortOrder = paramSortOrder!=null && paramSortOrder.equals("ASC") ? 
						ProducerBeanSortType.ASC : ProducerBeanSortType.DESC;
				sortType = new ProducerBeanSortType(sortBy, sortOrder);
				req.getSession().setAttribute(BEAN_SORT_TYPE_PREFIX+decoratorBean.getName(), sortType);
				return sortType;
			}catch(NumberFormatException skip){}
		}
		sortType = (ProducerBeanSortType)req.getSession().getAttribute(BEAN_SORT_TYPE_PREFIX+decoratorBean.getName());
		if (sortType==null)
			sortType = new ProducerBeanSortType();
		return sortType;
	}
}
