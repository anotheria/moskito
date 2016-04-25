package net.anotheria.moskito.webui.producers.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.registry.NoSuchProducerException;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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

	@GET @Path("{interval}/{timeunit}")
	public ReplyObject getProducers(@PathParam("interval") String intervalName, @PathParam("timeunit") String timeUnitParam){
		TimeUnit unit = TimeUnit.fromString(timeUnitParam);
		try{
			return ReplyObject.success("producers", producerAPI.getAllProducers(intervalName, unit));
		}catch(APIException e){
			return ReplyObject.error(e);
		}
	}

	@GET @Path("/byCategory/{category}/{interval}/{timeunit}")
	public ReplyObject getProducersByCategory(@PathParam("category") String category, @PathParam("interval") String intervalName, @PathParam("timeunit") String timeUnitParam){
		TimeUnit unit = TimeUnit.fromString(timeUnitParam);
		try{
			return ReplyObject.success("producers", producerAPI.getAllProducersByCategory(category, intervalName, unit));
		}catch(APIException e){
			return ReplyObject.error(e);
		}
	}

	@GET @Path("/bySubsystem/{subsystem}/{interval}/{timeunit}")
	public ReplyObject getProducersBySubsystem(@PathParam("subsystem") String subsystem, @PathParam("interval") String intervalName, @PathParam("timeunit") String timeUnitParam){
		TimeUnit unit = TimeUnit.fromString(timeUnitParam);
		try{
			return ReplyObject.success("producers", producerAPI.getAllProducersBySubsystem(subsystem, intervalName, unit));
		}catch(APIException e){
			return ReplyObject.error(e);
		}
	}

	@GET @Path("{producerId}/{interval}/{timeunit}")
	public ReplyObject getProducers(@PathParam("producerId") String producerId, @PathParam("interval") String intervalName, @PathParam("timeunit") String timeUnitParam){
		TimeUnit unit = TimeUnit.fromString(timeUnitParam);
		try{
			return ReplyObject.success("producer", producerAPI.getProducer(producerId, intervalName, unit));
		}catch(APIException e){
			return ReplyObject.error(e);
		}catch(NoSuchProducerException ee){
			return ReplyObject.error(ee);
		}

	}


	@GET @Path("categories")
	public ReplyObject getCategories(){
		try{
			return ReplyObject.success("categories", producerAPI.getCategories());
		}catch(APIException e){
			return ReplyObject.error(e);
		}
	}

	@GET @Path("subsystems")
	public ReplyObject getSubsystems(){
		try{
			return ReplyObject.success("subsystems", producerAPI.getSubsystems());
		}catch(APIException e){
			return ReplyObject.error(e);
		}
	}
}
