package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 13.02.13 18:13
 */
public interface AccumulatorAPI extends API {
	AccumulatorDefinitionAO createAccumulator(AccumulatorPO formBean) throws APIException;

	void removeAccumulator(String id) throws APIException;

	List<AccumulatorDefinitionAO> getAccumulatorDefinitions() throws APIException;

	AccumulatorDefinitionAO getAccumulatorDefinition(String id) throws APIException;

	AccumulatedSingleGraphAO getAccumulatorGraphData(String id) throws APIException;
}
