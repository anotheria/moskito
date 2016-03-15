package net.anotheria.moskito.webui.util.offlinecharts;

public final class OfflineChartGeneratorFactory {

    private static OfflineChartGenerator generator = new OfflineChartGeneratorImpl();

    public static OfflineChartGenerator getGenerator(){
        return generator;
    }
}