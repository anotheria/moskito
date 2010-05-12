package net.java.dev.moskito.core;

import net.java.dev.moskito.core.dynamic.DynamicTestSuite;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({MoskitoCoreTestSuite.class, DynamicTestSuite.class})
public class AllTests {
	@BeforeClass public static void init(){
		System.setProperty("JUNITTEST", "true");
	}
}
