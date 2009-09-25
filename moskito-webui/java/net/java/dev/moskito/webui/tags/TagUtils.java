package net.java.dev.moskito.webui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

public class TagUtils {

	private static Logger log = Logger.getLogger(TagUtils.class);
	
	private static enum Scope{
		/**
		 * page scope.
		 */
		page(PageContext.PAGE_SCOPE),
		/**
		 * request scope.
		 */
		request(PageContext.REQUEST_SCOPE),
		/**
		 * session scope.
		 */
		session(PageContext.SESSION_SCOPE),
		/**
		 * application scope.
		 */
		application(PageContext.APPLICATION_SCOPE);
		/**
		 * Corresponding constant value in PageContext.
		 */
		private int pageContextScope;
		
		private Scope(int aPageContextScope){
			pageContextScope = aPageContextScope;
		}
		
		public int getPageContextScope(){
			return pageContextScope;
		}
		
		
		
	}
	
	public static int getScope(String scopeName) throws JspException {
		return Scope.valueOf(scopeName).getPageContextScope();
    }
	
	
	public static Object lookup(PageContext pageContext, String scopeName, String beanName, String propertyName) throws JspException {
		
		Object bean = lookup(pageContext, scopeName, beanName);
		if (bean == null) {
			return null;
		}
        
		if (propertyName == null) {
			return bean;
		}

		try {
			Object property = PropertyUtils.getProperty(bean, propertyName);
			return property;
		} catch (Exception e) {
			log.error(e,e);
			throw new JspException("Could not read " + beanName + "." + propertyName, e);
		} 
	}
	
	public static Object lookup(PageContext pageContext, String scopeName, String aBeanName) throws JspException {
		String beanName = aBeanName;
		if(beanName == null) {
			beanName = "box";
		}
		
		if (scopeName == null) {
			Object bean = pageContext.findAttribute(beanName);
			if(bean == null && log.isDebugEnabled()) {
				log.debug("Did not find " + beanName + " in any scope.");
			}
			return bean;
		}

		Object bean = pageContext.getAttribute(beanName, getScope(scopeName));
		if(bean == null && log.isDebugEnabled()) {
			log.debug("Did not find " + beanName + " in scope " + scopeName);
		}
		return bean;
	}
	
	/**
	 * Puts an attribute into target scope.
	 * @param pageContext
	 * @param aScope
	 * @param anObjectName
	 * @param anBean
	 * @throws JspException
	 */
	public static void putAttribute(PageContext pageContext, String aScope, String anObjectName, Object anBean) throws JspException {
		if(aScope == null || aScope.length() == 0)
			aScope = "page";
		pageContext.setAttribute(anObjectName, anBean, getScope(aScope));
	}
	
	/**
	 * Writes a string to the page.
	 * @param pageContext
	 * @param s
	 * @throws JspException
	 */
	protected static void write(PageContext pageContext, String s) throws JspException{
		write(pageContext, s, false);		
	}
	
	/**
	 * Writes a string to the page.
	 * Writes to writer, associated with body of the tag we are currently nested within. 
	 * @param pageContext
	 * @param s
	 * @throws JspException
	 */
	protected static void writePrevious(PageContext pageContext, String s) throws JspException {
		write(pageContext, s, true);
	}
	
	private static void write(PageContext pageContext, String s, boolean toEnclosingWriter) throws JspException {
		JspWriter writer = pageContext.getOut();
		if(toEnclosingWriter && writer instanceof BodyContent)
			writer = ((BodyContent)writer).getEnclosingWriter();
		try {
			writer.print(s);
		} catch(IOException e) {
			throw new JspException("Input/output error: " + e.toString());
		}
	}
	
	/**
	 * Writes a string to the page and appends an empty line.
	 * @param pageContext
	 * @param s
	 * @throws JspException
	 */
	protected static void writeLn(PageContext pageContext, String s) throws JspException{
		write(pageContext, s+"\n");		
	}

	/**
	 * Quotes a string with double quotes.
	 * @param s
	 * @return
	 */
	protected static String quote(String s){
		return "\""+s+"\"";
	}
	/**
	 * Filters string for letters sensitive in HTML, replacing them with their HTML codes
	 * @param value - string to filter
	 * @return filtered value 
	 */
	protected static String filter(String value) {
		if(value == null)
			return null;
		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (char c : content) {
			switch(c) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			default:
				result.append(c);
			}
		}
		return result.toString();
	}

}

