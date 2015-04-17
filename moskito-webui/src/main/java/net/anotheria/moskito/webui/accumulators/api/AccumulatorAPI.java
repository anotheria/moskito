package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * API Implementation for accumulators. Provides access to stored accumulators, their values and allows to create new.
 *
 * @author lrosenberg
 * @since 13.02.13 18:13
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface AccumulatorAPI extends API, Service {
	/**
	 * Creates a new accumulator from user input.
	 * @param formBean data for accumulator creation.
	 * @return
	 * @throws APIException
	 */
	AccumulatorDefinitionAO createAccumulator(AccumulatorPO formBean) throws APIException;

	/**
	 * Removes an eisting accumulator by its ids.
	 * @param id
	 * @throws APIException
	 */
	void removeAccumulator(String id) throws APIException;

	List<AccumulatorDefinitionAO> getAccumulatorDefinitions() throws APIException;

	AccumulatorDefinitionAO getAccumulatorDefinition(String id) throws APIException;

	AccumulatedSingleGraphAO getAccumulatorGraphData(String id) throws APIException;

	AccumulatorAO getAccumulator(String id) throws APIException;

	AccumulatorAO getAccumulatorByName(String name) throws APIException;
}
