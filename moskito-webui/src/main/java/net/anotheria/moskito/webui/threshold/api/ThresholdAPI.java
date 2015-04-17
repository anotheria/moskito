package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.threshold.ThresholdConditionGuard;
import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * API for operations on thresholds.
 *
 * @author lrosenberg
 * @since 11.02.13 18:45
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface ThresholdAPI extends API, Service {

	List<ThresholdAlertAO> getAlerts() throws APIException;

	void createThreshold(ThresholdPO po) throws APIException;

	List<ThresholdStatusAO> getThresholdStatuses() throws APIException;

	List<ThresholdStatusAO> getThresholdStatuses(String ... names) throws APIException;

	List<ThresholdDefinitionAO> getThresholdDefinitions() throws APIException;

	void removeThreshold(String id) throws APIException;

	void updateThreshold(String thresholdId, ThresholdPO po) throws APIException;

	ThresholdStatus getWorstStatus() throws APIException;

	/**
	 * Returns threshold definition by id.
	 * @param id
	 * @return
	 * @throws APIException
	 */
	ThresholdDefinitionAO getThresholdDefinition(String id) throws APIException;

	/**
	 * Returns the worst status for the thresholds.
	 * @param thresholdNames
	 * @return
	 * @throws APIException
	 */
	ThresholdStatus getWorstStatus(List<String> thresholdNames) throws APIException;

	List<ThresholdConditionGuard> getGuardsForThreshold(String thresholdId) throws APIException;
}
