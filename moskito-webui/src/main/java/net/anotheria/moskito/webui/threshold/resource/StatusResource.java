package net.anotheria.moskito.webui.threshold.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;

/**
 * Returns the status of the application.
 *
 * @author lrosenberg
 * @since 14.04.15 18:09
 */
@Path("status")
//@Server(url = "/moskito-inspect-rest/")
public class StatusResource extends AbstractResource {

	@GET
	public ReplyObject getWorstStatus(){
		try{
			return ReplyObject.success("status", getThresholdAPI().getWorstStatus().name());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@POST
	public ReplyObject getWorstStatus(StatusForm statusForm){
		try{
			return ReplyObject.success("status", getThresholdAPI().getWorstStatus(statusForm.getThresholdNames()).name());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
