package net.anotheria.moskito.webui.producers.util;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.predefined.GenericStatsDecorator;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.stats.UnknownIntervalException;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.ProducerAOSortType;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;
import net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean;
import net.anotheria.util.sorter.StaticQuickSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author strel
 */
public class ProducerUtility {

    /**
     * Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ProducerUtility.class);


    //todo make separate method for graphData in future
    public static List<ProducerDecoratorBean> getDecoratedProducers(HttpServletRequest req, List<ProducerAO> producers, Map<String, GraphDataBean> graphData){

        Map<IDecorator, List<ProducerAO>> decoratorMap = new HashMap<>(producers.size());
        for (ProducerAO producer : producers){
            try{
                IDecorator decorator = findOrCreateDecorator(producer);
                List<ProducerAO> decoratoredProducers = decoratorMap.get(decorator);
                if (decoratoredProducers == null){
                    decoratoredProducers = new ArrayList<>();
                    decoratorMap.put(decorator, decoratoredProducers);

                    for(StatValueAO statBean : producer.getFirstStatsValues()){
                        String graphKey = decorator.getName()+ '_' +statBean.getName();
                        GraphDataBean graphDataBean = new GraphDataBean(decorator.getName()+ '_' +statBean.getJsVariableName(), statBean.getName());
                        graphData.put(graphKey, graphDataBean);
                    }
                }
                decoratoredProducers.add(producer);
            }catch(IndexOutOfBoundsException e){
                //producer has no stats at all, ignoring
            }
        }

        Set<Map.Entry<IDecorator, List<ProducerAO>>> entries = decoratorMap.entrySet();
        List<ProducerDecoratorBean> beans = new ArrayList<>(entries.size());
        for (Map.Entry<IDecorator, List<ProducerAO>> entry : entries){
            IDecorator decorator = entry.getKey();
            ProducerDecoratorBean b = new ProducerDecoratorBean();
            b.setName(decorator.getName());
            b.setCaptions(decorator.getCaptions());

            for (ProducerAO p : entry.getValue()) {
                try {
                    List<StatValueAO> values = p.getFirstStatsValues();
                    for (StatValueAO valueBean : values){
                        String graphKey = decorator.getName()+ '_' +valueBean.getName();
                        GraphDataBean bean = graphData.get(graphKey);
                        if (bean==null) {
                            // FIXME!
                            log.warn("unable to find bean for key: " + graphKey);
                        } else {
                            bean.addValue(new GraphDataValueBean(p.getProducerId(), valueBean.getRawValue()));
                        }
                    }
                }catch(UnknownIntervalException e){
                    //do nothing, apparently we have a decorator which has no interval support for THIS interval.
                }
            }
            b.setProducerBeans(StaticQuickSorter.sort(decoratorMap.get(decorator), getProducerBeanSortType(b, req)));
            beans.add(b);
        }

        return beans;
    }

    private static ProducerAOSortType getProducerBeanSortType(ProducerDecoratorBean decoratorBean, HttpServletRequest req){
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

    private static IDecorator findOrCreateDecorator(ProducerAO producer) {
        final IDecorator decorator = DecoratorRegistryFactory.getDecoratorRegistry().getDecorator(producer.getStatsClazzName());
        if (decorator instanceof GenericStatsDecorator) {
            final GenericStatsDecorator genericDecorator = (GenericStatsDecorator) decorator;
            if (!genericDecorator.isInitialized()) {
                for (StatValueAO statBean : producer.getFirstStatsValues()) {
                    genericDecorator.addCaption(statBean.getName(), statBean.getType());
                }
            }
            log.debug("for producer '" + producer.getProducerId() + "', a new generic stats decorator was created: " + decorator);
        } else {
            log.debug("for producer '" + producer.getProducerId() + "', a build-in decorator was created: " + decorator.getName());
        }
        return decorator;
    }
}
