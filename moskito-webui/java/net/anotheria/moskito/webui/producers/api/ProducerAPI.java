package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:49
 */
public interface ProducerAPI extends API {
	List<UnitCountAO> getCategories() throws APIException;

	List<UnitCountAO> getSubsystems() throws APIException;
}
