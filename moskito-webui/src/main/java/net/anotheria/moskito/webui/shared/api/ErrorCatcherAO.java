package net.anotheria.moskito.webui.shared.api;

import java.io.Serializable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.06.17 23:47
 */
public class ErrorCatcherAO implements Serializable{

	private static long serialVersionUID = 1;

	private String name;
	private int count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
