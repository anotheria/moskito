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
 * JAX-RS Resource that serves requests to accumulators.
 *
 * @author lrosenberg
 * @since 13.02.13 22:44
 */
@Path("accumulators")
public class AccumulatorResource {
	/**
	 * API Instance.
	 */
	private AccumulatorAPI accumulatorAPI = APIFinder.findAPI(AccumulatorAPI.class);

	/**
	 * Returns all accumulators.
	 * @return
	 */
	@GET
	@Path("list")
	public ReplyObject getAccumulators(){
		try{
			return ReplyObject.success("accumulators", accumulatorAPI.getAccumulatorDefinitions() );
		}catch(APIException e){
			throw new WebApplicationException(e);
		}

	}

	/**
	 * Removes one accumulator by id.
	 * @param id
	 * @return
	 */
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

	/**
	 * Returns an accumulator by id.
	 * @param id of the accumulator to return.
	 * @return
	 */
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

	/**
	 * Creates new accumulator.
	 * @param po the accumulator parameter object.
	 * @return
	 */
	@POST @Path("create")
	public ReplyObject createAccumulator(AccumulatorPO po){
		try{
			AccumulatorDefinitionAO ret = accumulatorAPI.createAccumulator(po);
			return ReplyObject.success("created", ret);
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}


}
