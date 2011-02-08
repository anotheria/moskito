package net.java.dev.moskito.webcontrol.configuration;

import net.anotheria.util.StringUtils;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(name = "moskitowc-servers", allfields = true)
public enum ServersConfig {
	
	INSTANCE;

	private String[] hosts;
	
	private String appPath;
	
	private String username;
	
	private String password;
	
	private boolean https = false;
	
	private ServersConfig(){
		ConfigurationManager.INSTANCE.configure(this);
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isHttps() {
		return https;
	}
	
	public boolean hasCredentials(){
		return !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password);
	}

	public void setHttps(boolean https) {
		this.https = https;
	}
	
	public String makeURL(String host, String producerPath){
		String scheme = isHttps()? "https://": "http://";
		String credentials = hasCredentials()? username + ":" + password + "@": "";
		return  scheme + credentials + host + normalizePath(appPath) + normalizePath(producerPath);
	}
	
	private String normalizePath(String path){
		if(!path.startsWith("/"))
			path = "/" + path;
		if(path.endsWith("/"))
			path = path.substring(0, path.length() - 1);
		return path;
	}
	
//	public List<String> getBaseUrls(){
//		List<String> ret = new ArrayList<String>(hosts.length);
//		for(String host: hosts)
//			ret.add(makeBaseURL(host));
//		return ret;
//	}

}
