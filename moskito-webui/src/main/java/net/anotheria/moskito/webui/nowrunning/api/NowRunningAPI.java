package net.anotheria.moskito.webui.nowrunning.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:23
 */
@DistributeMe(agentsSupport=false, moskitoSupport=false)
@SupportService
@FailBy(strategyClass= RetryCallOnce.class)
public interface NowRunningAPI extends API, Service {
	/**
	 * Returns the entry points with now-running and past-running actions.
	 * @return
	 * @throws APIException
	 */
	List<EntryPointAO> getEntryPoints() throws APIException;

	/**
	 * Number of currently running requests.
	 * @return
	 * @throws APIException
	 */
	int getNowRunningCount() throws APIException;
}
