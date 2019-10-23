package net.anotheria.moskito.webui.threshold.resource;

import io.swagger.v3.oas.annotations.servers.Server;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Rest resource, returns alerts in the system.
 *
 * @author lrosenberg
 * @since 14.04.15 16:54
 */
@Path("alerts")
@Server(url = "/moskito-inspect-rest/")
public class AlertsResource extends AbstractResource {
	@GET
	public ReplyObject getAlerts(){
		try{
			return ReplyObject.success("alerts", getThresholdAPI().getAlerts());
		}catch(APIException e){
			return ReplyObject.error(e.getMessage());
		}
	}
}
