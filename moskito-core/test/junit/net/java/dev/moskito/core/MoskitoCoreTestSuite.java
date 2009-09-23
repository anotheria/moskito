package net.java.dev.moskito.core;
import net.java.dev.moskito.core.predefined.RequestOrientedStatsTest;
import net.java.dev.moskito.core.predefined.ServiceStatsParallelTest;
import net.java.dev.moskito.core.predefined.ServiceStatsSimpleTest;
import net.java.dev.moskito.core.stats.impl.IntervalNameParserTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({ServiceStatsParallelTest.class, ServiceStatsSimpleTest.class, IntervalNameParserTest.class, RequestOrientedStatsTest.class})
public class MoskitoCoreTestSuite {

}
