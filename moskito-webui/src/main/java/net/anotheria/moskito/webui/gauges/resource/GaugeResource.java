package net.anotheria.moskito.webui.gauges.resource;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.15 11:47
 */
@Path("gauges")
//@Server(url = "/moskito-inspect-rest/")
public class GaugeResource extends AbstractResource {
	@GET
	public ReplyObject getGauges(){
		try{
			ReplyObject ret = ReplyObject.success();
			ret.addResult("gauges", getGaugeAPI().getGauges());
			return ret;
		}catch(APIException e){
			throw new WebApplicationException(e);
		}
	}
}
