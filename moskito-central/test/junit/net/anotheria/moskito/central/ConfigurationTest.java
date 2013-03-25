package net.anotheria.moskito.central;

import net.anotheria.moskito.central.config.Configuration;
import net.anotheria.moskito.central.config.StorageConfigEntry;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 11:53
 */
public class ConfigurationTest {
	@Test
	public void testTestConfiguration(){
		Configuration config = Central.getInstance().getConfiguration();
		StorageConfigEntry[] entries = config.getStorages();
		assertNotNull(entries);
		//System.out.println(Arrays.toString(entries));
	}
}
