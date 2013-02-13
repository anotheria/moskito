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
	 * Dashboard menu item.
	 */
	DASHBOARD("Dashboard"),
	/**
	 * Producers menu item.
	 */
	PRODUCERS("Producers"),
	/**
	 * Monitoring journeys navi item.
	 */
	JOURNEYS("Journeys"),
	/**
	 * Charts navi item.
	 */
	CHARTS("Charts"),
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
	 * Navigation item for threads.
	 */
	THREADS("Threads"),
	/**
	 * Collections for additional items
	 */
	MORE("More")
	;
	
	
	/**
	 * Current menu.
	 */
	private static ArrayList<NaviItem> menu;
	static{
		menu = new ArrayList<NaviItem>();
		menu.add(DASHBOARD);
		menu.add(PRODUCERS);
		menu.add(THRESHOLDS);
		menu.add(JOURNEYS);
		menu.add(CHARTS);
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
}
