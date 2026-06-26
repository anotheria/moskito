package net.anotheria.moskito.core.config.dashboards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChartConfigTest {

    @Test
    public void buildCaptionAccumulatorsEmpty() throws Exception {
        ChartConfig chartConfig = new ChartConfig();
        assertThrows(NullPointerException.class, chartConfig::buildCaption);
    }

    @Test
    public void buildCaptionAccumulatorsNonEmpty() throws Exception {
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.setAccumulators(new String[] {"fair", "is", "foul", "and", "foul", "is", "fair"});
        String caption = chartConfig.buildCaption();
        assertEquals("and fair fair foul foul is is", caption);
    }

}