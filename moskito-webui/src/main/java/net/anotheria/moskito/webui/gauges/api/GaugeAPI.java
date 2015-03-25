package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.15 11:40
 */
@DistributeMe(agentsSupport=false)
@SupportService
public interface GaugeAPI extends API, Service{
	List<GaugeAO> getGauges() throws APIException;
}
