package net.anotheria.moskito.webui.accumulators.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 13.02.13 22:44
 */
@Path("accumulators")
public class AccumulatorResource {
	private AccumulatorAPI accumulatorAPI = APIFinder.findAPI(AccumulatorAPI.class);

	@GET
	@Path("list")
	public ReplyObject getAccumulators(){
		try{
			return ReplyObject.success("accumulators", accumulatorAPI.getAccumulatorDefinitions() );
		}catch(APIException e){
			throw new WebApplicationException(e);
		}

	}

	@GET
	@Path("remove/{id}")
	public ReplyObject deleteAccumulator(@PathParam("id") String id){
		try{
			accumulatorAPI.removeAccumulator(id);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("get/{id}")
	public ReplyObject getAccumulator(@PathParam("id") String id){
		try{
			ReplyObject ro = ReplyObject.success();
			ro.addResult("accumulator", accumulatorAPI.getAccumulatorDefinition(id));
			ro.addResult("graphData", accumulatorAPI.getAccumulatorGraphData(id));
			return ro;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@POST @Path("create")
	public ReplyObject createAccumulator(AccumulatorPO po){
		System.out.println("CREATING "+po);
		try{
			AccumulatorDefinitionAO ret = accumulatorAPI.createAccumulator(po);
			return ReplyObject.success("created", ret);
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}


}
