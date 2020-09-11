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
	 * Navigation item for load factors.
	 */
	LOADFACTORS("LoadFactors"),
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
	 * Navigation item for errors.
	 */
	ERRORS("Errors"),
	/**
	 * Navigation item for tags.
	 */
	TAGS("Tags"),

	/**
	 * List of currently running threads.
	 */
	THREADS_LIST("List"),
	/**
	 * Generates a ThreadDump.
	 */
	THREADS_DUMP("Dump"),
	/**
	 * History of thread creation feature.
	 */
	THREADS_HISTORY("History"),
	/**
	 * Displays libs in the system.
	 */
	MORE_LIBS("Libs"),
	/**
	 * Shows config.
	 */
	MORE_CONFIG("Config"),
	/**
	 * Shows the update pane.
	 */
	MORE_UPDATE("Update"),
	/**
	 * Shows mbeans.
	 */
	MORE_MBEANS("MBeans"),
	/**
	 * Navi item for gauges.
	 */
	MORE_GAUGES("Gauges"),

	MORE_LOADFACTORS("Loadfactors"),

	MORE_NOWRUNNING("Nowrunning"),
	/**
	 * Navi item for kill switch.
	 */
	MORE_KILLSWITCH("Kill Switch");
	
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
