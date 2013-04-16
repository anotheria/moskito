package net.anotheria.moskito.central.endpoints.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import net.anotheria.moskito.central.Central;
import net.anotheria.moskito.central.Snapshot;

/**
 * Central REST resource for incoming snapshots via HTTP.
 * 
 * @author dagafonov
 * 
 */
@Path("/central")
public class RestEndpoint {

	private Central central;

	public RestEndpoint() {
		central = Central.getInstance();
	}

	/**
	 * Receives {@link Snapshot} in order to transfer it to the central.
	 * 
	 * @param sn
	 */
	@PUT
	@Path("/addSnapshot")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void addSnapshot(Snapshot sn) {
		central.processIncomingSnapshot(sn);
	}

}
