package net.anotheria.moskito.central.connectors.rest;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;

import net.anotheria.moskito.central.Snapshot;
import net.anotheria.moskito.central.SnapshotMetaData;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.spi.client.ClientFactory;

/**
 * 
 * @author dagafonov
 * 
 */
public class RestConnectorTest extends JerseyTest {

	public RestConnectorTest() {
		super("net.anotheria.moskito.central.endpoints.rest", "org.codehaus.jackson.jaxrs");
	}

	@Override
	protected ClientFactory getClientFactory() {
		return new ClientFactory() {
			@Override
			public Client create(ClientConfig cc) {
				cc.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
				return Client.create(cc);
			}
		};
	}

//	@Test
//	public void testGetSnapshot() throws Exception {
//		WebResource webResource = resource();
//		String responseString = webResource.path("/central/getSnapshot").accept(MediaType.APPLICATION_JSON).get(String.class);
//		System.out.println("*************************************\r\nresponseString=" + responseString);
//
//		Snapshot responseSnapshot = webResource.path("/central/getSnapshot").accept(MediaType.APPLICATION_JSON).get(Snapshot.class);
//		// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\r\nresponseSnapshot="
//		// + responseSnapshot);
//		assertNotNull(responseSnapshot);
//		assertNotNull(responseSnapshot.getStats());
//
//		HashMap<String, HashMap<String, String>> stats = responseSnapshot.getStats();
//		assertNotNull(stats);
//		assertEquals(3, stats.size());
//		assertNotNull(stats.get("test"));
//		assertNotNull(stats.get("test2"));
//		assertNotNull(stats.get("test3"));
//
//		HashMap<String, String> test = stats.get("test");
//		assertNotNull(test);
//		assertEquals(2, test.size());
//		assertNotNull(test.get("firstname"));
//		assertNotNull(test.get("lastname"));
//	}

	@Test
	public void testAddSnapshot() throws InterruptedException {

		WebResource webResource = resource();

		Snapshot sn = new Snapshot();

		SnapshotMetaData metaData = new SnapshotMetaData();
		metaData.setProducerId("prodId");
		metaData.setCategory("catId");
		metaData.setSubsystem("subSId");

		sn.setMetaData(metaData);

		HashMap<String, String> data = new HashMap<String, String>();
		data.put("firstname", "sidor");
		data.put("lastname", "sidorov");
		sn.addSnapshotData("test", data);
		sn.addSnapshotData("test2", data);
		sn.addSnapshotData("test3", data);

		webResource.path("/central/addSnapshot").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(sn);

		Thread.sleep(1000);

	}

}
