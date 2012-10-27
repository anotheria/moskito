package net.anotheria.moskito.core.journey;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 27.10.12 23:31
 */
public class JourneyManagerImplTest {
	@Test
	public void testCreateDelete() throws Exception{
		String journeyName = "myJourney";
		JourneyManagerImpl manager = new JourneyManagerImpl();
		try{
			manager.getJourney(journeyName);
			fail("Exception expected");
		}catch(NoSuchJourneyException e){}
		assertEquals(0, manager.getJourneys().size());


		Journey j1 = manager.createJourney(journeyName);
		Journey j2 = manager.getJourney(journeyName);
		assertSame(j1, j2);
		assertEquals(1, manager.getJourneys().size());
		assertEquals(j1, manager.getJourneys().get(0));

		//cleanup
		manager.removeJourney(j1);
		try{
			manager.getJourney(journeyName);
			fail("Exception expected");
		}catch(NoSuchJourneyException e){}
		assertEquals(0, manager.getJourneys().size());

	}
}
