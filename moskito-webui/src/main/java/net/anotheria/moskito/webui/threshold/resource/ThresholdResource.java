package net.anotheria.moskito.webui.threshold.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 * This handles thresholds in the moskito-inspect rest interface.
 *
 * @author lrosenberg
 * @since 11.02.13 18:24
 */
@Path("/thresholds")
public class ThresholdResource extends AbstractResource{

	@GET
	@Path("list")
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
	@Path("create")
	public ReplyObject createThreshold(ThresholdPO po){
		try{
			getThresholdAPI().createThreshold(po);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("remove/{id}")
	public ReplyObject deleteThreshold(@PathParam("id") String id){
		try{
			getThresholdAPI().removeThreshold(id);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("alerts")
	public ReplyObject getAlerts(){
		try{
			return ReplyObject.success("status", getThresholdAPI().getAlerts());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET @Path("worstStatus")
	public ReplyObject getWorstStatus(){
		try{
			return ReplyObject.success("status", getThresholdAPI().getWorstStatus());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@POST @Path("worstStatusForSelectedThresholds")
	public ReplyObject getWorstStatus(StatusForm statusForm){
		try{
			return ReplyObject.success("status", getThresholdAPI().getWorstStatus(statusForm.getThresholdNames()));
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
