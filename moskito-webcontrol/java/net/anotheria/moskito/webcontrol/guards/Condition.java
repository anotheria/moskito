package net.anotheria.moskito.webcontrol.guards;

/**
 * 
 * @author bee
 */
public enum Condition {

    /**
     * 
     */
    BLACK("black", 100),

    /*
     * 
     */
    GREEN("green", 50),

    /**
     * 
     */
    YELLOW("yellow", 20),

    /**
     * 
     */
    RED("red", 10);

    /**
     * 
     */
    public static final Condition DEFAULT = BLACK;

    /**
     * 
     */
    private String color;

    /**
     * 
     */
    private int priority;

    /**
     * Default constructor.
     * 
     * @param aColor
     * @param aPriprity
     */
    private Condition(String aColor, int aPriprity) {
	this.color = aColor;
	this.priority = aPriprity;
    }

    public String getColor() {
	return color;
    }

    public int getPriority() {
	return priority;
    }

    /**
     * Return Condition instance by specified color value.
     * 
     * @param aColor
     *            color itself
     * @return instance with specified color or throws error if such does not
     *         exist
     */
    public static Condition getValueByColor(String aColor) {
	for (Condition condition : Condition.values())
	    if (condition.getColor().equalsIgnoreCase(aColor))
		return condition;

	// log exception
	return DEFAULT;
    }

}
