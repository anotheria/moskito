package net.anotheria.moskito.webui.topproducers.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * Api that exposes the top-producers ranking maintained by
 * {@link net.anotheria.moskito.core.topproducers.TopProducersRepository}. It is the single access point for all
 * presentation surfaces (web ui, rest, mcp) and, being a distributeme service, is also reachable remotely - the same
 * way moskito-inspect-standalone reaches the other apis.
 *
 * @author lrosenberg
 */
@DistributeMe(agentsSupport = false, moskitoSupport = false)
@SupportService
@FailBy(strategyClass = RetryCallOnce.class)
public interface TopProducersAPI extends API, Service {
	/**
	 * Returns the top producers of a single ranking category, ordered descending by their accumulated score.
	 * @param category name of the ranking category (see {@link net.anotheria.moskito.core.topproducers.Category}).
	 * @param limit maximum number of producers to return, a value {@code <= 0} means no limit.
	 * @return the top producers of the category.
	 * @throws APIException if the category name is unknown.
	 */
	List<TopProducerAO> getTopProducers(String category, int limit) throws APIException;

	/**
	 * Returns the top producers of every ranking category.
	 * @param limit maximum number of producers per category, a value {@code <= 0} means no limit.
	 * @return the top producers grouped by category.
	 * @throws APIException if the ranking can not be accessed.
	 */
	List<CategoryTopProducersAO> getTopProducersByAllCategories(int limit) throws APIException;

	/**
	 * Returns the names of all available ranking categories.
	 * @return the ranking category names.
	 * @throws APIException if the categories can not be accessed.
	 */
	List<String> getCategories() throws APIException;
}
