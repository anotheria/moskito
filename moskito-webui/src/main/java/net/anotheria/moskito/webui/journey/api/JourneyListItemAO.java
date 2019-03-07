package net.anotheria.moskito.webui.journey.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * This bean contains an item in the journey list.
 * @author lrosenberg.
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class JourneyListItemAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -3336903807095768651L;

	/**
	 * Name of the session.
	 */
	private String name;
	/**
	 * Date of session creation.
	 */
	private String created;
	/**
	 * Last activity of the journey.
	 */
	private String lastActivity;
	/**
	 * True if the session is still active (recorded).
	 */
	private boolean active;
	/**
	 * Number of calls in this journey.
	 */
	private int numberOfCalls;

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfCalls() {
		return numberOfCalls;
	}
	public void setNumberOfCalls(int numberOfCalls) {
		this.numberOfCalls = numberOfCalls;
	}
	
	@Override public String toString(){
		return "Name: "+name+", "+"C: "+created+", LA: "+lastActivity+", NC: "+numberOfCalls+" A: "+active;
	}
}
