package net.java.dev.moskito.core.registry;

import java.lang.ref.WeakReference;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * This tests doesn't test moskito itself, but tests its underlying technology (weak ref usage for registry). 
 * In case it fails (due do jdk changes or something) its an indicator that something is wrong with moskito registry reference maintaining.
 * @author lrosenberg
 *
 */
public class RegistryMemoryLeakTest {
	private static WeakReference<String> s;
	
	@Test public void testWeakRef(){
		first();
		second();
		third();
	}
	
	public void first(){
		s = new WeakReference<String>(new String("TEST"));
	}
	public void second(){
		assertNotNull(s.get());
	}
	public void third(){
		System.gc();
		assertNull(s.get());
	}
}
