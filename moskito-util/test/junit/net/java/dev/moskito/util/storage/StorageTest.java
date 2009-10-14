package net.java.dev.moskito.util.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
public class StorageTest {
	@Test public void testStats(){
		Storage<String,Integer> storage = Storage.createHashMapStorage("test");
		
		assertTrue("Storage must be empty at start", storage.isEmpty());
		
		storage.put("a", 1);
		storage.put("a", 2);
		storage.put("a", 3);
		storage.put("a", 4);
		
		//produce a missing get
		storage.get("b");
		//produce a real get
		storage.get("a");
		//again
		storage.get("a");
		storage.get("a");
		
		
		assertTrue(storage.containsKey("a"));
		assertFalse(storage.containsKey("b"));
		
		assertTrue(storage.containsValue(4));
		assertFalse(storage.containsValue(3));

		StorageStats stats = (StorageStats) storage.getStats().get(0);
		assertFalse(storage.isEmpty());
		assertEquals(4, stats.getGets(null));
		assertEquals(1, stats.getMissedGets(null));
		assertEquals(0.25, stats.getMissedGetRatio(null), 0.01);
		assertEquals(0.75, stats.getHitGetRatio(null), 0.01);
		
		assertEquals(4, stats.getPuts(null));
		assertEquals(0.75, stats.getOverwritePutRatio(null), 0.01);
		assertEquals(3, stats.getOverwritePuts(null));
		
		assertEquals(1, storage.size());
		assertEquals(storage.size(), stats.getSize(null));
		
		storage.remove("c");
		storage.remove("c");
		storage.remove("c");
		assertEquals(3, stats.getNoopRemoves(null));
		storage.put("foo", 1);
		assertEquals(new Integer(1), storage.get("foo"));
		assertEquals(new Integer(1), storage.remove("foo"));
		assertNull(storage.remove("foo"));
		assertEquals(5, stats.getRemoves(null));
		assertEquals(4, stats.getNoopRemoves(null));
		
	}
	
	@Test public void testCreate(){
		int initialSize = 100;
		String name = "foo";
		assertNotNull(Storage.createHashMapStorage());
		assertNotNull(Storage.createHashMapStorage( initialSize));
		assertNotNull(Storage.createHashMapStorage( name));
		assertNotNull(Storage.createHashMapStorage( name,  initialSize));
		assertNotNull(Storage.createConcurrentHashMapStorage());
		assertNotNull(Storage.createConcurrentHashMapStorage( initialSize));
		assertNotNull(Storage.createConcurrentHashMapStorage( name));
		assertNotNull(Storage.createConcurrentHashMapStorage( name,  initialSize));
		assertNotNull(Storage.createHashtableStorage());
		assertNotNull(Storage.createHashtableStorage( initialSize));
		assertNotNull(Storage.createHashtableStorage( name));
		assertNotNull(Storage.createHashtableStorage( name,  initialSize));

	}
	
}
