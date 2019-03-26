package net.anotheria.moskito.webui.shared.bean;

/**
 * Alert type used at UI.
 *
 * @author Illya Bogatyrchuk
 */
public enum AlertType {
    SUCCESS("alert-success"),
    INFO("alert-info"),
    WARNING("alert-warning"),
    DANGER("alert-danger");

    private String cssStyle;

    AlertType(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getCssStyle() {
        return cssStyle;
    }
}
