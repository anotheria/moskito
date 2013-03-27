package net.anotheria.moskito.central;

/**
 * Contains various constants.
 *
 * @author lrosenberg
 * @since 23.03.13 00:41
 */
public class CentralConstants {
	/**
	 * Name of the property to set host name from outside.
	 */
	public static final String PROP_HOSTNAME = "hostname";
	/**
	 * Name of the property to set component name from outside.
	 */
	public static final String PROP_COMPONENT = "component";

	/**
	 * Tag part of the path pattern that is replaced by hostname.
	 */
	public static final String PATH_TAG_HOST = "{host}";
	/**
	 * Tag part of the path pattern that is replaced by component.
	 */
	public static final String PATH_TAG_COMPONENT = "{component}";
	/**
	 * Tag part of the path pattern that is replaced by producer id.
	 */
	public static final String PATH_TAG_PRODUCER = "{producer}";
	/**
	 * Tag part of the path pattern that is replaced by date of snapshot creation.
	 */
	public static final String PATH_TAG_DATE = "{date}";
	/**
	 * Tag part of the path pattern that is replaced by time of snapshot creation.
	 */
	public static final String PATH_TAG_TIME = "{time}";
	/**
	 * Tag part of the path pattern that is replaced by category.
	 */
	public static final String PATH_TAG_CATEGORY = "{category}";
	/**
	 * Tag part of the path pattern that is replaced by subsystem.
	 */
	public static final String PATH_TAG_SUBSYSTEM = "{subsystem}";
	/**
	 * Tag part of the path pattern that is replaced by interval name.
	 */
	public static final String PATH_TAG_INTERVAL = "{interval}";
	/**
	 * Tag part of the path pattern that is replaced by statname..
	 */
	public static final String PATH_TAG_STAT = "{stat}";



}
