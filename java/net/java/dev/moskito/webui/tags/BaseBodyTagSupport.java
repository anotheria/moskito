package net.java.dev.moskito.webui.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Base tag for body tags.
 * @author another
 *
 */
public class BaseBodyTagSupport extends BodyTagSupport{
	/**
	 * Target scope.
	 */
	private String scope;
	/**
	 * Id of the object to create.
	 */
	private String id;
	/**
	 * Name of the attribute in the scope.
	 */
	private String name;
	/**
	 * Property of the attribute under 'name'.
	 */
	private String property;
	
	/**
	 * Looks an object in the given scope/name/property and subProperty up.
	 * @return
	 * @throws JspException
	 */
	protected Object lookup() throws JspException{
		return TagUtils.lookup(pageContext, getScope(), getName(), getProperty());
	}
	/**
	 * Writes a string into the page.
	 * @param s
	 * @throws JspException
	 */
	protected void write(String s) throws JspException{
		TagUtils.write(pageContext, s);		
	}
	
	/**
	 * Write a string with a line break into the page.
	 * @param s
	 * @throws JspException
	 */
	protected void writeLn(String s) throws JspException{
		TagUtils.write(pageContext, s+"\n");		
	}

	/**
	 * Surrounds a string by double quotes.
	 * @param s
	 * @return
	 */
	protected String quote(String s){
		return TagUtils.quote(s);
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}


}
