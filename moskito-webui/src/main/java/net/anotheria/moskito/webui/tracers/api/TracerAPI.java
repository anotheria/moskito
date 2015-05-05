package net.anotheria.moskito.webui.tracers.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 00:42
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface TracerAPI extends API, Service {
	List<TracerAO> getTracers() throws APIException;

	TracerAO getTracer(String producerId) throws APIException;

	List<TraceAO> getTraces(String producerId, TimeUnit timeUnit) throws APIException;

	void createTracer(String producerId) throws APIException;

	void removeTracer(String producerId) throws APIException;

	void disableTracer(String producerId) throws APIException;
}
