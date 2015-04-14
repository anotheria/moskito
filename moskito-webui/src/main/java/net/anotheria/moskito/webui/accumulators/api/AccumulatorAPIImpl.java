package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.accumulation.AccumulatedValue;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorDefinition;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.SortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the AccumulatorAPI.
 *
 * @author lrosenberg
 * @since 13.02.13 18:13
 */
public class AccumulatorAPIImpl extends AbstractMoskitoAPIImpl implements AccumulatorAPI {

	/**
	 * Object for caching of the sort types.
	 */
	private static final SortType SORT_TYPE = new DummySortType();

	@Override
	public AccumulatorDefinitionAO createAccumulator(AccumulatorPO po) throws APIException{
		AccumulatorDefinition ad = new AccumulatorDefinition();
		ad.setName(po.getName());
		ad.setProducerName(po.getProducerId());
		ad.setStatName(po.getStatName());
		ad.setValueName(po.getValueName());
		ad.setIntervalName(po.getInterval());
		if (po.getUnit()==null || po.getUnit().length()==0)
			ad.setTimeUnit(TimeUnit.MILLISECONDS); //set to default
		else
			ad.setTimeUnit(TimeUnit.fromString(po.getUnit()));
		Accumulator acc = AccumulatorRepository.getInstance().createAccumulator(ad);
		return getAccumulatorDefinition(acc.getId());
	}

	@Override
	public void removeAccumulator(String id) throws APIException {
		AccumulatorRepository.getInstance().removeById(id);
	}

	@Override public AccumulatorAO getAccumulator(String id) throws APIException{
		return new AccumulatorAO(AccumulatorRepository.getInstance().getById(id));
	}

	@Override public AccumulatorAO getAccumulatorByName(String name) throws APIException{
		return new AccumulatorAO(AccumulatorRepository.getInstance().getByName(name));
	}

	public AccumulatorDefinitionAO getAccumulatorDefinition(String accId) throws APIException{
		Accumulator a = AccumulatorRepository.getInstance().getById(accId);
		AccumulatorDefinitionAO bean = new AccumulatorDefinitionAO();

		bean.setName(a.getName());
		bean.setPath(a.getDefinition().describe());
		bean.setId(a.getId());
		List<AccumulatedValue> values = a.getValues();
		if (values!=null && values.size()>0){
			bean.setNumberOfValues(values.size());
			bean.setLastValueTimestamp(values.get(values.size()-1).getISO8601Timestamp());
		}else{
			bean.setNumberOfValues(0);
			bean.setLastValueTimestamp("none");
		}
		return bean;
	}

	@Override
	public AccumulatedSingleGraphAO getAccumulatorGraphData(String id) throws APIException {
		Accumulator accumulator = AccumulatorRepository.getInstance().getById(id);
		AccumulatedSingleGraphAO singleGraphDataBean = new AccumulatedSingleGraphAO(accumulator.getName());

		singleGraphDataBean.setData(new AccumulatorAO(accumulator).getValues());
		return singleGraphDataBean;
	}

	@Override
	public List<AccumulatorDefinitionAO> getAccumulatorDefinitions() throws APIException {
		List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();
		List<AccumulatorDefinitionAO> ret = new ArrayList<AccumulatorDefinitionAO>();

		for (Accumulator a : accumulators){
			AccumulatorDefinitionAO bean = new AccumulatorDefinitionAO();

			bean.setName(a.getName());
			bean.setPath(a.getDefinition().describe());
			bean.setId(a.getId());
			bean.setMaxNumberOfValues(a.getDefinition().getMaxAmountOfAccumulatedItems());
			List<AccumulatedValue> values = a.getValues();
			if (values!=null && values.size()>0){
				bean.setNumberOfValues(values.size());
				bean.setLastValueTimestamp(values.get(values.size()-1).getISO8601Timestamp());
			}else{
				bean.setNumberOfValues(0);
				bean.setLastValueTimestamp("none");
			}

			ret.add(bean);
		}

		ret = StaticQuickSorter.sort(ret, SORT_TYPE);
		return ret;

	}
}
