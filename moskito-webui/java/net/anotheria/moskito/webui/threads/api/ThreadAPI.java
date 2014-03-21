package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * API for thread operations.
 *
 * @author lrosenberg
 * @since 14.02.13 11:45
 */
@DistributeMe(agentsSupport=false)
@SupportService
public interface ThreadAPI extends API, Service {
	List<ThreadInfoAO> getThreadInfos() throws APIException;
}
