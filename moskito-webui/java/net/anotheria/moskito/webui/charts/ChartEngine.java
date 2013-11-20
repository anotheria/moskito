package net.anotheria.moskito.webui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.11.13 13:46
 */
public enum ChartEngine {
	GOOGLE_CHART_API("google", "googlechart", "googlechartapi"){
		public boolean requiresNumericTimestamp(){ return false;}
	},
	HIGHCHART("high_chart", "highchart"),
	JQPLOT("jq_plot", "jqplot"),
	RAPHAELJS("raphaeljs", "raphael"),
	;

	private List<String> names;

	private ChartEngine(String... someNames){
		names = new ArrayList<String>();
		if (someNames!=null){
			for (String s :someNames){
				names.add(s.toLowerCase());
			}
		}
	}

	public static ChartEngine getChartEngine(String aName){
		if (aName!=null && aName.length()>0){
			for (ChartEngine e : values()){
				if (e.names.contains(aName))
					return e;
			}
		}
		//default
		return GOOGLE_CHART_API;
	}

	public boolean requiresNumericTimestamp(){
		return true;
	}
}
