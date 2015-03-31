package net.anotheria.moskito.webui.shared.bean;

import java.util.ArrayList;
import java.util.List;

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
	 * Collections for additional items
	 */
	MORE("Everything else"),

	THREADS_LIST("List"), THREADS_DUMP("Dump"), THREADS_HISTORY("History"),
	MORE_LIBS("Libs"),MORE_PLUGINS("Plugins"),MORE_CONFIG("Config"),MORE_UPDATE("Update"),MORE_MBEANS("MBeans"), MORE_GAUGE("Gauge")
	;
	
	
	/**
	 * Current menu.
	 */
	private static ArrayList<NaviItem> menu;
	static{
		menu = new ArrayList<NaviItem>();
		menu.add(DASHBOARDS);
		menu.add(PRODUCERS);
		menu.add(THRESHOLDS);
		menu.add(JOURNEYS);
		menu.add(ACCUMULATORS);
		menu.add(THREADS);
		menu.add(MORE);
		//menu.add(USECASES);
	}
	/**
	 * Caption of the navi item.
	 */
	private String caption;
	
	private NaviItem(String aCaption){
		caption = aCaption;
	}
	
	public String getId(){
		return toString().toLowerCase();
	}
	
	public String getCaption(){
		return caption;
	}
	
	public static final List<NaviItem> getMenu(){
		return menu;
	}

	public boolean isSelected(String id){
		return getId().equals(id);
	}
}
