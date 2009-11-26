package net.java.dev.moskito.webcontrol.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.java.dev.moskito.webcontrol.testsupport.DummyFillRepository;

import org.junit.Test;

public class DummyFeedRepository {
	

	@Test public void testRepository(){
		DummyFillRepository.fillRepository();
		
		List<Snapshot> snapshots = Repository.INSTANCE.getSnapshots("TestMem");
		assertEquals(3, snapshots.size());
		for (Snapshot s : snapshots){
			assertNotNull(s.toString());
			System.out.println(s.toString());
		}
		
	}
}
