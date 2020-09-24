package net.anotheria.moskito.webui.dashboards;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPIImpl;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.08.16 22:36
 */
public class DashboardsAPITest {

	private DashboardAPIImpl api = new DashboardAPIImpl();

	@BeforeClass public static void setup(){
		APIFinder.setMockingEnabled(true);
	}


	@Test public void ensureRemoveChartFromDashboardDoesntFailOnErrors() throws APIException{
		api.removeChartFromDashboard(null, new String[]{"Q1", "Q2", "Q3"});
		api.removeChartFromDashboard("not existing", null);
		api.removeChartFromDashboard("testDashboard", new String[]{"Q1", "Q2", "Q3"});
		api.removeChartFromDashboard("testDashboard", null);

	}

	@Test
	public void removeChartFromDashboardRemoveFirstElement() throws APIException{

		api.removeChartFromDashboard("testDashboard", new String[]{"A1", "A2", "A3"});
		ensureTestWorked(4, "A");
	}

	@Test public void removeChartFromDashboardRemoveLastElement() throws APIException{
		api.removeChartFromDashboard("testDashboard", new String[]{"E1", "E2", "E3"});
		ensureTestWorked(4, "E");
	}

	@Test public void removeChartFromDashboardRemoveMiddleElement() throws APIException{
		api.removeChartFromDashboard("testDashboard", new String[]{"C1", "C2", "C3"});
		ensureTestWorked(4, "C");
	}

	private void ensureTestWorked(int expectedSize, String expectedMissingName) throws APIException{
		ChartConfig[] charts = api.getDashboardConfig("testDashboard").getCharts();
		assertEquals(expectedSize, charts.length);
		for (ChartConfig c : charts){
			assertNotEquals(expectedMissingName, c.getCaption());
		}
	}

	@Before public void prepareMoskitoConfig(){
		MoskitoConfiguration myConfig = new MoskitoConfiguration();

		DashboardConfig myFirstDashboard = new DashboardConfig();
		myFirstDashboard.setName("FirstTest");

		DashboardConfig myTestDashboard = new DashboardConfig();
		myTestDashboard.setName("testDashboard");

		ChartConfig c1 = new ChartConfig(); c1.setCaption("A"); c1.setAccumulators(new String[]{"A1","A2","A3"});
		ChartConfig c2 = new ChartConfig(); c2.setCaption("B"); c2.setAccumulators(new String[]{"B1","B2","B3"});
		ChartConfig c3 = new ChartConfig(); c3.setCaption("C"); c3.setAccumulators(new String[]{"C1","C2","C3"});
		ChartConfig c4 = new ChartConfig(); c4.setCaption("D"); c4.setAccumulators(new String[]{"D1","D2","D3"});
		ChartConfig c5 = new ChartConfig(); c5.setCaption("E"); c5.setAccumulators(new String[]{"E1","E2","E3"});
		myTestDashboard.setCharts(new ChartConfig[]{c1, c2, c3, c4, c5});

		DashboardsConfig dashboardsConfig = new DashboardsConfig();
		dashboardsConfig.setDashboards(new DashboardConfig[]{myFirstDashboard, myTestDashboard});
		myConfig.setDashboardsConfig(dashboardsConfig);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(myConfig);
	}

}
