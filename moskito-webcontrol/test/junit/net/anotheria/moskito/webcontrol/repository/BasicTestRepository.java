package net.anotheria.moskito.webcontrol.repository;

import net.anotheria.moskito.webcontrol.repository.AttributeFactory;
import net.anotheria.moskito.webcontrol.repository.AttributeType;
import net.anotheria.moskito.webcontrol.repository.Snapshot;
import net.anotheria.moskito.webcontrol.repository.SnapshotSource;

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
		
		assertEquals(s1.hashCode(), s2.hashCode());
		assertFalse(s1.hashCode() == s3.hashCode());
		
		assertTrue(s1.toString().equals("test"));
	}
	
	@Test public void testSnapshot(){
		SnapshotSource source = new SnapshotSource("test");
		long now = System.currentTimeMillis();
		
		Snapshot s = new Snapshot(source);
		
		assertEquals(source, s.getSource());
		assertTrue(s.getCreatedTimestamp()>=now);
		
		String string1 = s.toString();
		assertNotNull(string1);
		
		s.addAttribute(AttributeFactory.create(AttributeType.LONG, "a", "1"));
		String string2 = s.toString();
		assertNotNull(string2);
		assertFalse(string1.equals(string2));
		
		s.removeAttribute(AttributeFactory.create(AttributeType.LONG, "b", "1"));
		String string3 = s.toString();
		assertNotNull(string3);
		assertFalse(string1.equals(string3));
		assertTrue(string2.equals(string3));
		
		//this should have no effect
		s.addAttribute(AttributeFactory.create(AttributeType.LONG, "a", "1"));
		String string4 = s.toString();
		assertNotNull(string4);
		assertTrue(string3.equals(string4));
		
		s.removeAttribute(AttributeFactory.create(AttributeType.LONG, "a", "1"));
		String string5 = s.toString();
		assertNotNull(string5);
		assertTrue(string1.equals(string5));
		
		
//		System.out.println(string1);
//		System.out.println(string2);		
//		System.out.println(string3);
//		System.out.println(string4);
//		System.out.println(string5);
		
		
	}
	
}
