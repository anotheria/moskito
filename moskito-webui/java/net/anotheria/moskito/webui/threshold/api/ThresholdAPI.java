package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.core.threshold.ThresholdStatus;

import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.02.13 18:45
 */
@DistributeMe(agentsSupport=false)
@SupportService
public interface ThresholdAPI extends API, Service {

	List<ThresholdAlertAO> getAlerts() throws APIException;

	Threshold createThreshold(ThresholdPO po) throws APIException;

	List<ThresholdStatusAO> getThresholdStatuses() throws APIException;

	List<ThresholdDefinitionAO> getThresholdDefinitions() throws APIException;

	void removeThreshold(String id) throws APIException;

	void updateThreshold(String thresholdId, ThresholdPO po) throws APIException;

	ThresholdStatus getWorstStatus() throws APIException;

	/**
	 * Returns the worst status for the thresholds.
	 * @param thresholdNames
	 * @return
	 * @throws APIException
	 */
	ThresholdStatus getWorstStatus(List<String> thresholdNames) throws APIException;
}
