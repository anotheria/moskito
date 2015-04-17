package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * This API provides access to gauges.
 *
 * @author lrosenberg
 * @since 25.03.15 11:40
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface GaugeAPI extends API, Service{
	/**
	 * Returns all configured gauges with their current min/max/current values.
	 * @return
	 * @throws APIException
	 */
	List<GaugeAO> getGauges() throws APIException;

	/**
	 * Returns named gauges and their values. This method is used by the dashboard.
	 * @param names
	 * @return
	 * @throws APIException
	 */
	List<GaugeAO> getGauges(String ... names) throws APIException;
}
