package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.util.StringUtils;

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

	public String getDecoratorNameForCss(){
		return StringUtils.removeChars(getName(), BaseMoskitoUIAction.WHITESPACES);
	}
}
