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
 */
@DistributeMe(agentsSupport=false, moskitoSupport=false)
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
	 * Removes an existing accumulator by its ids.
	 * @param id
	 * @throws APIException
	 */
	void removeAccumulator(String id) throws APIException;

	/**
	 * Returns all accumulator definitions.
	 * @return
	 * @throws APIException
	 */
	List<AccumulatorDefinitionAO> getAccumulatorDefinitions() throws APIException;

	/**
	 * Returns an accumulator definition by id.
	 * @param id definition id.
	 * @return
	 * @throws APIException
	 */
	AccumulatorDefinitionAO getAccumulatorDefinition(String id) throws APIException;

	AccumulatedSingleGraphAO getAccumulatorGraphData(String id) throws APIException;

	AccumulatedSingleGraphAO getAccumulatorGraphDataByName(String name) throws APIException;

	/**
	 * Retrieves an accumulator by generated id.
	 * @param id accumulator id.
	 * @return
	 * @throws APIException
	 */
	AccumulatorAO getAccumulator(String id) throws APIException;

	/**
	 * Returns an accumulator by its name.
	 * @param name name of the accumulator.
	 * @return
	 * @throws APIException
	 */
	AccumulatorAO getAccumulatorByName(String name) throws APIException;

	MultilineChartAO getNormalizedAccumulatorGraphData(List<String> ids) throws APIException;

	MultilineChartAO getCombinedAccumulatorGraphData(List<String> ids) throws APIException;

	List<AccumulatedSingleGraphAO> getChartsForMultipleAccumulators(List<String> ids) throws APIException;

	/**
	 * Returns all accumulator ids which are tied to a specific producer.
	 * @param producerId
	 * @return
	 * @throws APIException
	 */
	List<String> getAccumulatorIdsTiedToASpecificProducer(String producerId) throws APIException;

}
