package net.java.dev.moskito.webui.bean;

import java.util.List;

/**
 * This bean contains explanations for decorators.
 * @author lrosenberg.
 *
 */
public class DecoratorExplanationBean {
	/**
	 * Name of the decorator.
	 */
	private String name;
	/**
	 * Captions for the decorator.
	 */
	private List<StatCaptionBean> captions;
	public List<StatCaptionBean> getCaptions() {
		return captions;
	}
	public void setCaptions(List<StatCaptionBean> captions) {
		this.captions = captions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
