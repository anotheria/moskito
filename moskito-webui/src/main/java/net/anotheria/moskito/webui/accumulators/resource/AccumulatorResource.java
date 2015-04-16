package net.anotheria.moskito.webui.accumulators.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.DELETE;
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
public class AccumulatorResource extends AbstractResource{

	/**
	 * Returns all accumulators.
	 * @return
	 */
	@GET
	public ReplyObject getAccumulators(){
		try{
			return ReplyObject.success("accumulators", getAccumulatorAPI().getAccumulatorDefinitions() );
		}catch(APIException e){
			throw new WebApplicationException(e);
		}

	}

	/**
	 * Removes one accumulator by id.
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public ReplyObject deleteAccumulator(@PathParam("id") String id){
		try{
			getAccumulatorAPI().removeAccumulator(id);
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
	@Path("/{id}")
	public ReplyObject getAccumulator(@PathParam("id") String id){
		try{
			ReplyObject ro = ReplyObject.success();
			ro.addResult("accumulator", getAccumulatorAPI().getAccumulatorDefinition(id));
			ro.addResult("chartData", getAccumulatorAPI().getAccumulatorGraphData(id));
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
	@POST
	public ReplyObject createAccumulator(AccumulatorPO po){
		try{
			AccumulatorDefinitionAO ret = getAccumulatorAPI().createAccumulator(po);
			return ReplyObject.success("created", ret);
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}


}
