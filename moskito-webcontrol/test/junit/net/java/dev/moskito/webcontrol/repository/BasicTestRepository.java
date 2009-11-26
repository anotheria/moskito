package net.java.dev.moskito.webcontrol.repository;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicTestRepository {
	@Test public void testSnapshotSource(){
		SnapshotSource s1 = new SnapshotSource("test");
		SnapshotSource s2 = new SnapshotSource(new StringBuilder().append("te").append("st").toString());
		SnapshotSource s3 = new SnapshotSource("not test");
		
		assertEquals(s1,s2);
		assertEquals(s1,s1);
		assertEquals(s3,s3);
		
		assertFalse(s3.equals(s1));
		assertFalse(s1.equals(s3));
	}
}
