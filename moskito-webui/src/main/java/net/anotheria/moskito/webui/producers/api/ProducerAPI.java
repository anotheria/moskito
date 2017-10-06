package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * API for producer operations.
 *
 * @author lrosenberg
 * @since 14.02.13 11:49
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface ProducerAPI extends API, Service {
	/**
	 * Returns all available categories.
	 * @return
	 * @throws APIException
	 */
	List<UnitCountAO> getCategories() throws APIException;

	/**
	 * Returns all available subsystems.
	 * @return
	 * @throws APIException
	 */
	List<UnitCountAO> getSubsystems() throws APIException;

	/**
	 * Returns all available producers with stats by intervalname and timeunit.
	 * @param intervalName
	 * @param timeUnit time unit for duration calculation.
	 * @return
	 * @throws APIException
	 */
	List<ProducerAO> getAllProducers(String intervalName, TimeUnit timeUnit)throws APIException;

	/**
	 * Returns all producers in given category.
	 * @param currentCategory category filter.
	 * @param intervalName
	 * @param timeUnit time unit for duration calculation.
	 * @return
	 * @throws APIException
	 */
	List<ProducerAO> getAllProducersByCategory(String currentCategory, String intervalName, TimeUnit timeUnit)throws APIException;

	/**
	 *
	 * @param iProducerFilters
	 * @param intervalName
	 * @param timeUnit time unit for duration calculation.
	 * @return
	 * @throws APIException
	 */
	List<ProducerAO> getProducers(IProducerFilter[] iProducerFilters, String intervalName, TimeUnit timeUnit)throws APIException;

	/**
	 *
	 * @param producerIds
	 * @param intervalName
	 * @param timeUnit time unit for duration calculation.
	 * @return
	 * @throws APIException
	 */
	List<ProducerAO> getProducers(List<String> producerIds, String intervalName, TimeUnit timeUnit) throws APIException;

	/**
	 * Returns all producers in a given subsystem.
	 * @param currentSubsystem subsystem filter.
	 * @param intervalName
	 * @param timeUnit time unit for duration calculation.
	 * @return
	 * @throws APIException
	 */
	List<ProducerAO> getAllProducersBySubsystem(String currentSubsystem, String intervalName, TimeUnit timeUnit)throws APIException;

	/**
	 * Returns performance data for a single producer.
	 * @param producerId id of the producer.
	 * @param intervalName name of the interval.
	 * @param timeUnit timeUnit for the duration calculation.
	 * @return
	 * @throws APIException
	 */
	ProducerAO getProducer(String producerId, String intervalName, TimeUnit timeUnit) throws APIException;
}
