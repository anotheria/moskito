package net.java.dev.moskito.core;
import net.java.dev.moskito.core.blueprint.BlueprintTestWithCalc;
import net.java.dev.moskito.core.logging.StatsLoggerTest;
import net.java.dev.moskito.core.predefined.RequestOrientedStatsTest;
import net.java.dev.moskito.core.predefined.ServiceStatsParallelTest;
import net.java.dev.moskito.core.predefined.ServiceStatsSimpleTest;
import net.java.dev.moskito.core.registry.ProducerRegistryTest;
import net.java.dev.moskito.core.stats.impl.IntValueHolderTest;
import net.java.dev.moskito.core.stats.impl.IntervalImplTest;
import net.java.dev.moskito.core.stats.impl.IntervalNameParserTest;
import net.java.dev.moskito.core.stats.impl.LongValueHolderTest;
import net.java.dev.moskito.core.stats.impl.NegativeCurrentRequestsTest;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({ServiceStatsParallelTest.class, ServiceStatsSimpleTest.class, IntervalNameParserTest.class, RequestOrientedStatsTest.class, NegativeCurrentRequestsTest.class, ProducerRegistryTest.class, BlueprintTestWithCalc.class,
	IntervalImplTest.class, IntValueHolderTest.class, LongValueHolderTest.class, StatsLoggerTest.class})
public class MoskitoCoreTestSuite {
	@BeforeClass public static void init(){
		System.setProperty("JUNITTEST", "true");
	}
	

}
