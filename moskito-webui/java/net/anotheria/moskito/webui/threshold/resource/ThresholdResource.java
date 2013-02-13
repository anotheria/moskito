package net.anotheria.moskito.webui.threshold.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.threshold.Threshold;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;
import net.anotheria.moskito.webui.threshold.bean.ThresholdBean;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 * This handles thresholds in the moskito-webui rest interface.
 *
 * @author lrosenberg
 * @since 11.02.13 18:24
 */
@Path("/thresholds")
public class ThresholdResource {

	private ThresholdAPI thresholdAPI = APIFinder.findAPI(ThresholdAPI.class);

	@GET
	@Path("list")
	public List<ThresholdBean> getThresholds(){
		return new ArrayList<ThresholdBean>();
	}

	@POST
	@Path("create")
	public ReplyObject createThreshold(ThresholdPO po){
		try{
			Threshold t = thresholdAPI.createThreshold(po);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("remove/{id}")
	public ReplyObject deleteThreshold(@PathParam("id") String id){
		try{
			thresholdAPI.removeThreshold(id);
			return ReplyObject.success();
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}

	@GET
	@Path("alerts")
	public ReplyObject getAlerts(){
		try{
			return ReplyObject.success(thresholdAPI.getAlerts());
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}


}
