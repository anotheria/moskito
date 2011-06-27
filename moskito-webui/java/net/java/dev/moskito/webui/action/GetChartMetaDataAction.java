package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.webui.bean.NaviItem;
import net.java.dev.moskito.webui.bean.ProducerAndTypeBean;
import net.java.dev.moskito.webui.bean.TypeAndValueNamesBean;

public class GetChartMetaDataAction extends BaseMoskitoUIAction{

	@Override
	protected NaviItem getCurrentNaviItem() {
		return null;
	}

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return null;
	}
	
	/**
	 * Link to the producer registry.
	 */
	private IProducerRegistry registry;
	/**
	 * Default constructor.
	 */
	public GetChartMetaDataAction(){
		registry = ProducerRegistryFactory.getProducerRegistryInstance();
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean form,
			HttpServletRequest req, HttpServletResponse res) {
		
		Collection<IStatsProducer> producers = registry.getProducers();
		Set<Class<? extends IStats>> statclasses = new HashSet<Class<? extends IStats>>();
		Map<Class<?extends IStats>, List<String>> stat2valueMap = new HashMap<Class<? extends IStats>, List<String>>();
		
		List<ProducerAndTypeBean> ptBeans = new ArrayList<ProducerAndTypeBean>();
		List<TypeAndValueNamesBean> tvnBeans = new ArrayList<TypeAndValueNamesBean>();
		
		for (IStatsProducer producer : producers){
			try{
				IStats stat = producer.getStats().get(0);
				if (!statclasses.contains(stat.getClass())){
					statclasses.add(stat.getClass());
					stat2valueMap.put(stat.getClass(), stat.getAvailableValueNames());
					if (stat.getAvailableValueNames().size()>0)
						tvnBeans.add(new TypeAndValueNamesBean(stat.getClass().getSimpleName(), stat.getAvailableValueNames()));
				}
				ProducerAndTypeBean patBean = new ProducerAndTypeBean(producer.getProducerId(), stat.getClass().getSimpleName());
				for (IStats s : producer.getStats()){
					if (s.getAvailableValueNames().size()>0)
						patBean.addStatName(s.getName());
				}
				ptBeans.add(patBean);
				
			}catch(IllegalArgumentException e){
				//emptyproducer, ignore
			}
		}

		
		req.setAttribute("producerAndTypes", ptBeans);
		req.setAttribute("typeAndValueNames", tvnBeans);

		return mapping.findCommand( getForward(req) );
	}
}
