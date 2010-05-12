package net.java.dev.moskito.core.dynamic;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({OnDemandStatsProducerTest.class, EntryCountLimitedOnDemandStatsProducerTest.class})
public class DynamicTestSuite {
	@BeforeClass public static void init(){
		System.setProperty("JUNITTEST", "true");
	}
}
