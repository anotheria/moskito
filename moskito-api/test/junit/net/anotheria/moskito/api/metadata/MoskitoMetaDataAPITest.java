package net.anotheria.moskito.api.metadata;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * {@link MoskitoMetaDataAPI} test.
 * 
 * @author Alexandr Bolbat
 */
public class MoskitoMetaDataAPITest {
	/**
	 * {@link MoskitoMetaDataAPI} instance.
	 */
	private static MoskitoMetaDataAPI moskitoMetaDataAPI;

	@BeforeClass
	public static void init() {
		moskitoMetaDataAPI = MoskitoMetaDataAPIFactory.getAPI();
	}

	@Test
	public void testIntervals() {
		List<String> intervals = moskitoMetaDataAPI.getIntervals();
		Assert.assertTrue("No intervals found: " + intervals.toString(), 0 < intervals.size());
	}

	@Test
	public void testSubsystems() {
		List<String> subsystems = moskitoMetaDataAPI.getSubsystems();
		Assert.assertTrue("No subsystems found: " + subsystems.toString(), 0 < subsystems.size());
	}

	@Test
	public void testCategories() {
		List<String> categories = moskitoMetaDataAPI.getCategories();
		Assert.assertTrue("No categories found: " + categories.toString(), 0 < categories.size());
	}

	@Test
	public void testProducers() {
		List<String> producers = moskitoMetaDataAPI.getProducers();
		Assert.assertTrue("No producers found: " + producers.toString(), 0 < producers.size());

		List<String> subsystems = moskitoMetaDataAPI.getSubsystems();
		producers = moskitoMetaDataAPI.getProducersBySubsystems(subsystems);
		Assert.assertTrue("No producers found: " + producers.toString(), 0 < producers.size());

		List<String> categories = moskitoMetaDataAPI.getCategories();
		producers = moskitoMetaDataAPI.getProducersByCategories(categories);
		Assert.assertTrue("No producers found: " + producers.toString(), 0 < producers.size());

		String subsystem = subsystems.get(0);
		producers = moskitoMetaDataAPI.getProducers(subsystem, categories);
		Assert.assertTrue("No producers found: " + producers.toString(), producers.size() == producers.size());
	}

	@Test
	public void testEmptyArguments() {
		List<String> producers = moskitoMetaDataAPI.getProducersBySubsystems(null);
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
		producers = moskitoMetaDataAPI.getProducersBySubsystems(new ArrayList<String>());
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());

		producers = moskitoMetaDataAPI.getProducersByCategories(null);
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
		producers = moskitoMetaDataAPI.getProducersByCategories(new ArrayList<String>());
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());

		producers = moskitoMetaDataAPI.getProducers(null, null);
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
		producers = moskitoMetaDataAPI.getProducers(null, new ArrayList<String>());
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
		producers = moskitoMetaDataAPI.getProducers("", null);
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
		producers = moskitoMetaDataAPI.getProducers("", new ArrayList<String>());
		Assert.assertTrue("No producers found should be: " + producers.toString(), 0 == producers.size());
	}

}
