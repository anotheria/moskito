package net.anotheria.moskito.webui.util;

import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 17:28
 */
@ConfigureMe(allfields = true)
public class RemoteInstance {
	/**
	 * Name of this instance.
	 */
	private String name;
	/**
	 * Host of this instance.
	 */
	private String host;
	/**
	 * Port on which the rmi registry of the remote instance is running.
	 */
	private int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name == null ||name.length()==0 ?
				host : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
		return getName()+"@"+getHost()+":"+getPort();
	}

	public String getSelectKey(){
		return getHost()+"-"+getPort();
	}

	public boolean equalsByKey(String key){
		return getSelectKey().equals(key);
	}

}
