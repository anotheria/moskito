package net.anotheria.moskito.webui.util;

import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration of an instance that is accessable via rmi.
 *
 * @author lrosenberg
 * @since 21.03.14 17:28
 */
@ConfigureMe(allfields = true)
public class RemoteInstance implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

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
		return name == null || name.isEmpty() ?
				host : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString(){
        return getName()+ '@' + host + ':' + port;
	}

	/**
	 * Returns a string key for select field (unique string so to say).
	 * @return
	 */
	public String getSelectKey(){
        return host + '-' + port;
	}

	/**
	 * Returns true if this object's select key (getSelectKey()) equals to the submitted value.
	 * @param key
	 * @return
	 */
	public boolean equalsByKey(String key){
		return getSelectKey().equals(key);
	}

}
