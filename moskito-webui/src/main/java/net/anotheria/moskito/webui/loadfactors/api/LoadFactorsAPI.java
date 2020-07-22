package net.anotheria.moskito.webui.loadfactors.api;

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
 * @since 22.07.20 16:40
 */
@DistributeMe(agentsSupport=false, moskitoSupport=false)
@SupportService
@FailBy(strategyClass= RetryCallOnce.class)
public interface LoadFactorsAPI extends API, Service {
	List<LoadFactorAO> getLoadFactors() throws APIException;
}
