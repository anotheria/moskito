package net.anotheria.moskito.webui.shared.bean;

/**
 * Items in the navigation.
 *
 * @author lrosenberg.
 */
public enum NaviItem {
	/**
	 * Producers menu item.
	 */
	PRODUCERS("Producers"),
	/**
	 * Monitoring journeys navi item.
	 */
	JOURNEYS("Journeys"),
	/**
	 * Constant for action without own navigation item.
	 */
	NONE("None"),
	/**
	 * Navigation item for thresholds.
 	 */
	THRESHOLDS("Thresholds"),
	/**
	 * Navigation item for accumulators.
	 */
	ACCUMULATORS("Accumulators"),
	/**
	 * Navigation item for dashboards.
	 */
	DASHBOARDS("Dashboards"),
	/**
	 * Navigation item for threads.
	 */
	THREADS("Threads"),
	/**
	 * Tracers.
	 */
	TRACERS("Tracers"),
	/**
	 * Plugins.
	 */
	PLUGINS("Plugins"),
	/**
	 * Collections for additional items
	 */
	MORE("Everything else"),
	/**
	 * Navigation item for tags.
	 */
	TAGS("Tags"),

	THREADS_LIST("List"), THREADS_DUMP("Dump"), THREADS_HISTORY("History"),
	MORE_LIBS("Libs"),MORE_CONFIG("Config"),MORE_UPDATE("Update"),MORE_MBEANS("MBeans"), MORE_GAUGES("Gauges"), MORE_ERRORS("Errors")
	;
	
	/**
	 * Caption of the navi item.
	 */
	private String caption;
	
	NaviItem(String aCaption){
		caption = aCaption;
	}
	
	public String getId(){
		return toString().toLowerCase();
	}
	
	public String getCaption(){
		return caption;
	}
	
	public boolean isSelected(String id){
		return getId().equals(id);
	}
}
