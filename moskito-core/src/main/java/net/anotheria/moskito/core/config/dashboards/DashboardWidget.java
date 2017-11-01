package net.anotheria.moskito.core.config.dashboards;

/**
 * Represents dashboard components - widgets.
 * Each widget has name, which is used in dashboard configuration and
 * path to jsp page, containing widget view code.
 *
 * @author strel
 */
public enum DashboardWidget {

    /**
     * Empty widget.
     */
    NONE("", ""),

    /**
     * Thresholds widget.
     */
    THRESHOLDS("thresholds", "/net/anotheria/moskito/webui/dashboards/jsp/widgets/ThresholdsWidget.jsp"),

    /**
     * Gauges widget.
     */
    GAUGES("gauges", "/net/anotheria/moskito/webui/dashboards/jsp/widgets/GaugesWidget.jsp"),

    /**
     * Charts widget.
     */
    CHARTS("charts", "/net/anotheria/moskito/webui/dashboards/jsp/widgets/ChartsWidget.jsp"),

    /**
     * Producers widget.
     */
    PRODUCERS("producers", "/net/anotheria/moskito/webui/dashboards/jsp/widgets/ProducersWidget.jsp");


    /**
     * Widget name.
     * Should match appropriate widget name in configuration.
     */
    private String name;

    /**
     * Path to jsp containing widget view code.
     */
    private String jspPath;


    DashboardWidget(String name, String jspPath) {
        this.name = name;
        this.jspPath = jspPath;
    }


    public String getName() {
        return name;
    }

    public String getJspPath() {
        return jspPath;
    }

    /**
     * Searches for widget with given name.
     * @param name Widget name to search for
     * @return Widget or none if there is no such widget.
     */
    public static DashboardWidget findWidgetByName(String name) {
        for (DashboardWidget widget : values()) {
            if (widget.getName().equals(name)) {
                return widget;
            }
        }

        return NONE;
    }

    @Override
    public String toString() {
        return name;
    }
}
