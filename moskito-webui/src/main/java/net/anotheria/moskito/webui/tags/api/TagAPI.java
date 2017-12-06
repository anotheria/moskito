package net.anotheria.moskito.webui.tags.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.FailBy;
import org.distributeme.annotation.SupportService;
import org.distributeme.core.failing.RetryCallOnce;

import java.util.List;

/**
 * API for operations on tags.
 *
 * @author esmakula
 */
@DistributeMe(agentsSupport=false)
@SupportService
@FailBy(strategyClass=RetryCallOnce.class)
public interface TagAPI extends API, Service {

	List<TagAO> getTags();

}
