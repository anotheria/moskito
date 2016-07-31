package net.anotheria.moskito.core.config.dashboards;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ChartConfigTest {

    @Test(expected = NullPointerException.class)
    public void buildCaptionAccumulatorsEmpty() throws Exception {
        ChartConfig chartConfig = new ChartConfig();
        String caption = chartConfig.buildCaption();
    }

    @Test
    public void buildCaptionAccumulatorsNonEmpty() throws Exception {
        ChartConfig chartConfig = new ChartConfig();
        chartConfig.setAccumulators(new String[] {"fair", "is", "foul", "and", "foul", "is", "fair"});
        String caption = chartConfig.buildCaption();
        assertThat(caption, is("fair is foul and foul is fair"));
    }

}