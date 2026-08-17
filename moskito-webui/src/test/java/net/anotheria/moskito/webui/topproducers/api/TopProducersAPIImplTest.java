package net.anotheria.moskito.webui.topproducers.api;

import net.anotheria.anoplass.api.APIException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests the data path of the {@link TopProducersAPIImpl} against the core repository. The ranking is empty in a plain
 * jvm (no producers registered), so this mainly asserts categories, argument validation and that the repository can be
 * accessed at all.
 */
public class TopProducersAPIImplTest {

	private final TopProducersAPIImpl api = new TopProducersAPIImpl();

	@Test
	public void returnsAllCategories() throws APIException {
		List<String> categories = api.getCategories();
		assertEquals(5, categories.size());
		assertNotNull(categories);
		org.junit.jupiter.api.Assertions.assertTrue(categories.contains("REQUESTS"));
		org.junit.jupiter.api.Assertions.assertTrue(categories.contains("ERROR_RATE"));
	}

	@Test
	public void returnsRankingForKnownCategory() throws APIException {
		//no producers registered -> empty but never null.
		assertNotNull(api.getTopProducers("REQUESTS", 10));
		//case insensitive.
		assertNotNull(api.getTopProducers("total_time", 10));
	}

	@Test
	public void rejectsUnknownCategory() {
		assertThrows(APIException.class, () -> api.getTopProducers("does-not-exist", 10));
		assertThrows(APIException.class, () -> api.getTopProducers(null, 10));
	}

	@Test
	public void acceptsBothScoreTypes() throws APIException {
		assertNotNull(api.getTopProducers("REQUESTS", 10, "ORDINAL"));
		assertNotNull(api.getTopProducers("REQUESTS", 10, "SHARE"));
		//case insensitive, and unset means the position based ranking.
		assertNotNull(api.getTopProducers("REQUESTS", 10, "share"));
		assertNotNull(api.getTopProducers("REQUESTS", 10, null));
		assertNotNull(api.getTopProducers("REQUESTS", 10, "  "));
		assertNotNull(api.getTopProducersByAllCategories(5, "SHARE"));
	}

	@Test
	public void rejectsUnknownScoreType() {
		assertThrows(APIException.class, () -> api.getTopProducers("REQUESTS", 10, "does-not-exist"));
		assertThrows(APIException.class, () -> api.getTopProducersByAllCategories(5, "does-not-exist"));
	}

	@Test
	public void returnsAllCategoriesBundle() throws APIException {
		List<CategoryTopProducersAO> all = api.getTopProducersByAllCategories(5);
		assertEquals(5, all.size());
		for (CategoryTopProducersAO category : all) {
			assertNotNull(category.getCategory());
			assertNotNull(category.getProducers());
		}
	}
}
