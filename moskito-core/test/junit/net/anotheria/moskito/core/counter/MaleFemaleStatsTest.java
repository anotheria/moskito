package net.anotheria.moskito.core.counter;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This test demonstrates how to use the MaleFemaleStats objects.
 * It emulates male/female page requests.
 *
 * @author lrosenberg
 * @since 18.11.12 17:49
 */
public class MaleFemaleStatsTest {
	@Test
	public void testPagesByGender() throws Exception{
		OnDemandStatsProducer<MaleFemaleStats> producer = new OnDemandStatsProducer<MaleFemaleStats>("pages", "category", "subsystem", new MaleFemaleStatsFactory());

		//male client comes to the homepage
		producer.getDefaultStats().incMale();
		producer.getStats("homepage").incMale();

		//female client comes to the homepage
		producer.getDefaultStats().incFemale();
		producer.getStats("homepage").incFemale();

		//male client comes to the messaging page
		producer.getDefaultStats().incMale();
		producer.getStats("messaging").incMale();



		//we had a total of 2 male users and 1 female user
		assertEquals(2, producer.getDefaultStats().getMale());
		assertEquals(1, producer.getDefaultStats().getFemale());

		//we had a total of 1 male user and 1 female user on the homepage
		assertEquals(1, producer.getStats("homepage").getMale());
		assertEquals(1, producer.getStats("homepage").getFemale());

		//we had a total of 1 male user and 0 female user on the messaging page.
		assertEquals(1, producer.getStats("messaging").getMale());
		assertEquals(0, producer.getStats("messaging").getFemale());
	}

}
