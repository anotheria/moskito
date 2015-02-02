package net.anotheria.moskito.webui.producers.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * Resource for producer related resources.
 *
 * @author lrosenberg
 * @since 14.02.13 09:51
 */
@Path("producers")
public class ProducerResource extends AbstractResource {

	/**
	 * ProducerAPI.
	 */
	private ProducerAPI producerAPI = APIFinder.findAPI(ProducerAPI.class);

	@GET @Path("shortlist")
	public ReplyObject shortList(){
		return null;
	}

	@GET @Path("categories")
	public ReplyObject getCategories(){
		try{
			return ReplyObject.success("categories", producerAPI.getCategories());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET @Path("subsystems")
	public ReplyObject getSubsystems(){
		try{
			return ReplyObject.success("subsystems", producerAPI.getSubsystems());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
