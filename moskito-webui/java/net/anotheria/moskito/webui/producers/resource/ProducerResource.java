package net.anotheria.moskito.webui.producers.resource;

import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.shared.resource.ReplyObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 09:51
 */
@Path("producers")
public class ProducerResource extends AbstractResource {

	@GET @Path("shortlist")
	public ReplyObject shortList(){
		return null;
	}
}
