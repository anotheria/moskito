package net.anotheria.moskito.core.config.dashboards;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DashboardConfigTest {

    @Test
    public void setChartPatterns(){
        DashboardConfig dashboardConfig = new DashboardConfig();

        ChartPattern chartPattern = new ChartPattern();
        chartPattern.setAccumulatorPatterns(new String[]{"pattern"});
        dashboardConfig.setChartPatterns(new ChartPattern[]{chartPattern});
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());

        chartPattern.setAccumulatorPatterns(new String[]{"pattern", "another pattern"});
        dashboardConfig.setChartPatterns(new ChartPattern[]{chartPattern});
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[1].pattern(), Pattern.compile("another pattern").pattern());

        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns().length, 2);
        dashboardConfig.setChartPatterns(new ChartPattern[]{chartPattern});
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns().length, 2);

        ChartPattern chartPattern1 = new ChartPattern();
        chartPattern1.setAccumulatorPatterns(new String[]{"3rd pattern", "4th pattern"});
        dashboardConfig.setChartPatterns(new ChartPattern[]{chartPattern, chartPattern1});
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[1].pattern(), Pattern.compile("another pattern").pattern());
        assertEquals(dashboardConfig.getChartPatterns()[1].getPatterns()[0].pattern(), Pattern.compile("3rd pattern").pattern());
        assertEquals(dashboardConfig.getChartPatterns()[1].getPatterns()[1].pattern(), Pattern.compile("4th pattern").pattern());

        assertEquals(dashboardConfig.getChartPatterns().length, 2);
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns().length, 2);
        assertEquals(dashboardConfig.getChartPatterns()[1].getPatterns().length, 2);
    }

    @Test
    public void setWrongChartPattern() {
        DashboardConfig dashboardConfig = new DashboardConfig();
        ChartPattern chartPattern = new ChartPattern();
        chartPattern.setAccumulatorPatterns(new String[]{"pattern", "*error", "another pattern"});
        dashboardConfig.setChartPatterns(new ChartPattern[]{chartPattern});

        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns()[1].pattern(), Pattern.compile("another pattern").pattern());
        assertEquals(dashboardConfig.getChartPatterns()[0].getPatterns().length, 2);

        assertArrayEquals(dashboardConfig.getChartPatterns()[0].getAccumulatorPatterns(), new String[]{"pattern", "*error", "another pattern"});
    }

    @Test
    public void setProducerNamePatterns() {
        DashboardConfig dashboardConfig = new DashboardConfig();

        dashboardConfig.setProducerNamePatterns(new String[]{"pattern"});
        assertEquals(dashboardConfig.getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());

        dashboardConfig.setProducerNamePatterns(new String[]{"pattern", "another pattern"});
        assertEquals(dashboardConfig.getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());
        assertEquals(dashboardConfig.getPatterns()[1].pattern(), Pattern.compile("another pattern").pattern());

        assertEquals(dashboardConfig.getPatterns().length, 2);
        dashboardConfig.setProducerNamePatterns(new String[]{"pattern", "another pattern"});
        assertEquals(dashboardConfig.getPatterns().length, 2);
    }

    @Test
    public void setWrongProducerNamePatterns(){
        DashboardConfig dashboardConfig = new DashboardConfig();

        dashboardConfig.setProducerNamePatterns(new String[]{"pattern", "*error"});
        assertEquals(dashboardConfig.getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());

        dashboardConfig.setProducerNamePatterns(new String[]{"pattern", "*error", "another pattern"});
        assertEquals(dashboardConfig.getPatterns()[0].pattern(), Pattern.compile("pattern").pattern());
        assertEquals(dashboardConfig.getPatterns()[1].pattern(), Pattern.compile("another pattern").pattern());

        assertEquals(dashboardConfig.getPatterns().length, 2);
        dashboardConfig.setProducerNamePatterns(new String[]{"pattern", "*error", "another pattern"});
        assertEquals(dashboardConfig.getPatterns().length, 2);

        assertArrayEquals(dashboardConfig.getProducerNamePatterns(), new String[]{"pattern", "*error", "another pattern"});
    }
}