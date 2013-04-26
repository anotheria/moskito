package net.anotheria.moskito.central.endpoints.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

	/**
	 * Central instance.
	 */
	private Central central;

	/**
	 * Derfault constructor.
	 */
	public RestEndpoint() {
		central = Central.getInstance();
	}
	
//	@GET
//	@Path("/getSnapshot")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Snapshot getSnapshot() {
//		Snapshot sn = new Snapshot();
//
//		SnapshotMetaData metaData = new SnapshotMetaData();
//		metaData.setProducerId("prodId");
//		metaData.setCategory("catId");
//		metaData.setSubsystem("subSId");
//
//		sn.setMetaData(metaData);
//
//		HashMap<String, String> data = new HashMap<String, String>();
//		data.put("firstname", "sidor");
//		data.put("lastname", "sidorov");
//		sn.addSnapshotData("test", data);
//		sn.addSnapshotData("test2", data);
//		sn.addSnapshotData("test3", data);
//
//		return sn;
//	}

	/**
	 * Receives {@link Snapshot} in order to transfer it to the central.
	 * 
	 * @param sn
	 */
	@POST
	@Path("/addSnapshot")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void addSnapshot(Snapshot sn) {
		central.processIncomingSnapshot(sn);
	}

}
