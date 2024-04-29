package net.anotheria.moskito.webui.dashboards.resource;


import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

/**
 * The REST Resource for Dashboards API.
 *
 * @author lrosenberg
 * @since 15.04.15 09:08
 */
@Path("dashboards")
//@Server(url = "/moskito-inspect-rest/")
public class DashboardResource extends AbstractResource {
	@GET
	public ReplyObject getDashboardDefinitions(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("definitions", getDashboardAPI().getDashboardDefinitions());
			return ret;
		}catch(APIException e){
			return ReplyObject.error(e.getMessage());
		}
	}

	/**
	 * Returns a dashboard by its name.
	 * @param name
	 * @return
	 */
	@GET @Path("/{name}")
	public ReplyObject getDashboard(@PathParam("name")String name){
		try{
			ReplyObject ret = ReplyObject.success();
			DashboardAO dashboard = getDashboardAPI().getDashboard(name);
			ret.addResult("dashboard", dashboard);
			return ret;
		}catch(APIException e){
			return ReplyObject.error(e.getMessage());
		}
	}

}
