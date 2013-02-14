package net.anotheria.moskito.webui.journey.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:38
 */
@Path("journeys")
public class JourneyResource extends AbstractResource {

	private JourneyAPI journeyAPI = APIFinder.findAPI(JourneyAPI.class);

	@GET @Path("list")
	public ReplyObject getJourneyList(){
		try{
			return ReplyObject.success("journeys", journeyAPI.getJourneys());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
