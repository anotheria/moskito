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
		api.removeChartFromDashboard(null, "not existing");
		api.removeChartFromDashboard("not existing", null);
		api.removeChartFromDashboard("testDashboard", "not existing");
		api.removeChartFromDashboard("testDashboard", null);

	}

	@Test
	public void removeChartFromDashboardRemoveFirstElement() throws APIException{

		api.removeChartFromDashboard("testDashboard", "A");
		ensureTestWorked(4, "A");
	}

	@Test public void removeChartFromDashboardRemoveLastElement() throws APIException{
		api.removeChartFromDashboard("testDashboard", "E");
		ensureTestWorked(4, "E");
	}

	@Test public void removeChartFromDashboardRemoveMiddleElement() throws APIException{
		api.removeChartFromDashboard("testDashboard", "C");
		ensureTestWorked(4, "C");
	}

	private void ensureTestWorked(int expectedSize, String expectedMissingName) throws APIException{
		ChartConfig[] charts = api.getDashboardConfig("testDashboard").getCharts();
		//System.out.println("Checking 4 "+expectedMissingName+" against "+ Arrays.toString(charts));
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

		ChartConfig c1 = new ChartConfig(); c1.setCaption("A");
		ChartConfig c2 = new ChartConfig(); c2.setCaption("B");
		ChartConfig c3 = new ChartConfig(); c3.setCaption("C");
		ChartConfig c4 = new ChartConfig(); c4.setCaption("D");
		ChartConfig c5 = new ChartConfig(); c5.setCaption("E");
		myTestDashboard.setCharts(new ChartConfig[]{c1, c2, c3, c4, c5});

		DashboardsConfig dashboardsConfig = new DashboardsConfig();
		dashboardsConfig.setDashboards(new DashboardConfig[]{myFirstDashboard, myTestDashboard});
		myConfig.setDashboardsConfig(dashboardsConfig);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(myConfig);
	}

}
