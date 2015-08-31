package net.anotheria.moskito.webui.util;

import java.util.ArrayList;
import java.util.List;

/**
 * this enum contains selection of supported engines. It may be suboptimal that the supported engines are hardcoded via enum,
 * and we may open it up via config in the future, if such a need will emerge.
 *
 * @author lrosenberg
 * @since 11.11.13 13:46
 */
public enum ChartEngine {
	GOOGLE_CHART_API("google", "googlechart", "googlechartapi"){
		public boolean requiresNumericTimestamp(){ return false;}
	},

	D3("d3"),

    HIGHCHART("highChart");

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
		return WebUIConfig.getInstance().getDefaultChartEngine();
	}

	public boolean requiresNumericTimestamp(){
		return true;
	}

	public List<String> getNames(){
		return names;
	}
}
