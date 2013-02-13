package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.threshold.Threshold;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.02.13 18:45
 */
public interface ThresholdAPI extends API {

	List<ThresholdAlertAO> getAlerts() throws APIException;

	Threshold createThreshold(ThresholdPO po) throws APIException;

	void removeThreshold(String parameter) throws APIException;
}
