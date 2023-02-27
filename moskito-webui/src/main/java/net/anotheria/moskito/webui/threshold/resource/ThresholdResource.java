package net.anotheria.moskito.webui.threshold.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;

/**
 * This resource handles thresholds in the moskito-inspect rest interface.
 *
 * @author lrosenberg
 * @since 11.02.13 18:24
 */
@Path("/thresholds")
//@Server(url = "/moskito-inspect-rest/")
public class ThresholdResource extends AbstractResource{

	@GET
	public ReplyObject getThresholds(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("statuses", getThresholdAPI().getThresholdStatuses());
			ret.addResult("definitions", getThresholdAPI().getThresholdDefinitions());
			return ret;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("definitions")
	public ReplyObject getThresholdDefinitions(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("definitions", getThresholdAPI().getThresholdDefinitions());
			return ret;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("statuses")
	public ReplyObject getThresholdStatuses(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("statuses", getThresholdAPI().getThresholdStatuses());
			return ret;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@POST
	public ReplyObject createThreshold(ThresholdPO po){
		try{
			getThresholdAPI().createThreshold(po);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@DELETE
	@Path("/{id}")
	public ReplyObject deleteThreshold(@PathParam("id") String id){
		try{
			getThresholdAPI().removeThreshold(id);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
