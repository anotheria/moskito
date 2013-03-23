package net.anotheria.moskito.central;

import net.anotheria.moskito.central.config.Configuration;
import net.anotheria.moskito.central.config.StorageConfigEntry;
import net.anotheria.moskito.central.storage.Storage;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Test for central routing into the storage mechanism.
 *
 * @author lrosenberg
 * @since 18.03.13 17:13
 */
public class CentralStorageRoutingTest {

	private static final Map<String, TestStorage> storages = new HashMap<String, TestStorage>();

	@Before
	public void setup(){
		storages.clear();
	}

	private Configuration createConfiguration(){
		Configuration c = new Configuration();

		StorageConfigEntry entry = new StorageConfigEntry();
		entry.setName("test");
		entry.setClazz(TestStorage.class.getName());
		entry.setConfigName("test");

		c.setStorages(new StorageConfigEntry[]{entry});

		return c;
	}

	@Test
	public void simpleTestRouting(){
		Configuration config = createConfiguration();
		Central myCentral = Central.getConfiguredInstance(config);

		for (int i=0; i<10; i++){
			myCentral.processIncomingSnapshot(new Snapshot());
		}

		assertEquals(10, storages.get("test").getReceivedSnapshots());

	}

	static class TestStorage implements Storage {

		private int receivedSnapshots = 0;

		@Override
		public void configure(String configurationName) {
			storages.put(configurationName, this);
		}

		@Override
		public void processSnapshot(Snapshot target) {
			receivedSnapshots++;
		}

		int getReceivedSnapshots(){
			return receivedSnapshots;
		}
	}
}
