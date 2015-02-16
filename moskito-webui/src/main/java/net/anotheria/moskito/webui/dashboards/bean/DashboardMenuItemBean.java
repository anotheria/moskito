package net.anotheria.moskito.webui.dashboards.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 14:16
 */
public class DashboardMenuItemBean {
	private String name;

	public DashboardMenuItemBean(String aName){
		name = aName;
	}

	public String getName() {
		return name;
	}

	public String getUrlParameter(){
		try{
			return URLEncoder.encode(name, "UTF-8");
		}catch(UnsupportedEncodingException e){
			//ignore
			return name;
		}
	}
}
