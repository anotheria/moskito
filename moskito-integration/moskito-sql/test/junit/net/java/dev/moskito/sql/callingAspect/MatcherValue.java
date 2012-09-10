package net.java.dev.moskito.sql.callingAspect;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 10:49 AM
 *         To change this template use File | Settings | File Templates.
 */
public interface MatcherValue {
    /**
     * Constant property name for "id" for internal storage and queries.
     */
    public static final String PROP_ID = "id";
    /**
     * Constant property name for "type" for internal storage and queries.
     */
    public static final String PROP_TYPE = "type";
    /**
     * Constant property name for "value" for internal storage and queries.
     */
    public static final String PROP_VALUE = "value";
    /**
     * Constant property name for "matcherId" for internal storage and queries.
     */
    public static final String LINK_PROP_MATCHER_ID = "matcherId";

    /**
     * Returns the value of the type attribute.
     */
    public int getType();

    /**
     * Sets the value of the type attribute.
     */
    public void setType(int value);

    /**
     * Returns the value of the value attribute.
     */
    public String getValue();

    /**
     * Sets the value of the value attribute.
     */
    public void setValue(String value);

    /**
     * Returns the value of the matcherId attribute.
     */
    public String getMatcherId();

    /**
     * Sets the value of the matcherId attribute.
     */
    public void setMatcherId(String value);

    String getId();
}
