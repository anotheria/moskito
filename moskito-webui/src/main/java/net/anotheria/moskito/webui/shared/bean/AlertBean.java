package net.anotheria.moskito.webui.shared.bean;

/**
 * Represents alert at UI.
 *
 * @author Illya Bogatyrchuk
 */
public class AlertBean {
    private AlertType type;
    private String message;
    private boolean animate;
    private boolean fullWidth;
    private boolean roundBorders;
    private boolean dismissible;

    private AlertBean(AlertType type, String message) {
        this.type = type;
        this.message = message;
    }

    public AlertType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAnimate() {
        return animate;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public boolean isFullWidth() {
        return fullWidth;
    }

    public void setFullWidth(boolean fullWidth) {
        this.fullWidth = fullWidth;
    }

    public boolean isRoundBorders() {
        return roundBorders;
    }

    public void setRoundBorders(boolean roundBorders) {
        this.roundBorders = roundBorders;
    }

    public boolean isDismissible() {
        return dismissible;
    }

    public void setDismissible(boolean dismissible) {
        this.dismissible = dismissible;
    }

    /**
     * Creates default alert without animation, with square borders and full width.
     */
    public static AlertBean create(AlertType type, String message) {
        AlertBean result = new AlertBean(type, message);
        result.setAnimate(false);
        result.setFullWidth(true);
        result.setRoundBorders(false);
        return result;
    }

    /**
     * Creates default alert as in {@link #create(AlertType, String)} with dismiss functionality.
     */
    public static AlertBean createDismissible(AlertType type, String message) {
        final AlertBean result = create(type, message);
        result.setDismissible(true);
        return result;
    }

    /**
     * Creates small animated alert with round borders.
     */
    public static AlertBean createSmallAnimatedRounded(AlertType type, String message) {
        AlertBean result = new AlertBean(type, message);
        result.setAnimate(true);
        result.setFullWidth(false);
        result.setRoundBorders(true);
        return result;
    }
}
