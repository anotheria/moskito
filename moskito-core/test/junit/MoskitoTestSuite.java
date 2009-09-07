import net.java.dev.moskito.core.predefined.ServiceStatsParallelTest;
import net.java.dev.moskito.core.predefined.ServiceStatsSimpleTest;
import net.java.dev.moskito.core.stats.impl.IntervalNameParserTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses({ServiceStatsParallelTest.class, ServiceStatsSimpleTest.class, IntervalNameParserTest.class})
public class MoskitoTestSuite {

}
