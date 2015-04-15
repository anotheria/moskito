package net.anotheria.moskito.webui.dashboards.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.04.15 09:08
 */
@Path("dashboards")
public class DashboardResource extends AbstractResource {
	@GET
	public ReplyObject getDashboardDefinitions(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("definitions", getDashboardAPI().getDashboardDefinitions());
			return ret;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
