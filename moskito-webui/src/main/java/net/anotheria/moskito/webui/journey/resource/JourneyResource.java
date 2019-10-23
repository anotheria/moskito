package net.anotheria.moskito.webui.journey.resource;

import io.swagger.v3.oas.annotations.servers.Server;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * REST Resource for journey endpoint.
 *
 * @author lrosenberg
 * @since 14.02.13 10:38
 */
@Path("journeys")
@Server(url = "/moskito-inspect-rest/")
public class JourneyResource extends AbstractResource {

	@GET @Path("list")
	public ReplyObject getJourneyList(){
		try{
			return ReplyObject.success("journeys", getJourneyAPI().getJourneys());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
