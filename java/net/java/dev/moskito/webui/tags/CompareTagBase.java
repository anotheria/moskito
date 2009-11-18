package net.java.dev.moskito.webui.tags;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.PropertyUtils;

public abstract class CompareTagBase extends BaseTagSupport {

	protected static final int DOUBLE_COMPARE = 0;
	protected static final int LONG_COMPARE = 1;
	protected static final int STRING_COMPARE = 2;

	private String value = null;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		return condition() ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void release() {
		super.release();
		value = null;
	}

	protected abstract boolean condition() throws JspException;

	protected boolean condition(int desired1, int desired2) throws JspException {

		// Acquire the value and determine the test type
		int type = -1;
		double doubleValue = 0.0;
		long longValue = 0;
		if ((type < 0) && (value.length() > 0)) {
			try {
				doubleValue = Double.parseDouble(value);
				type = DOUBLE_COMPARE;
			} catch (NumberFormatException e) {
			}
		}
		if ((type < 0) && (value.length() > 0)) {
			try {
				longValue = Long.parseLong(value);
				type = LONG_COMPARE;
			} catch (NumberFormatException e) {
			}
		}
		if (type < 0) {
			type = STRING_COMPARE;
		}

		String name = getName();
		String property = getProperty();
		
		Object variable = null;
		if (name != null) {
			Object bean = pageContext.findAttribute(name);
			if (property != null) {
				if (bean == null) {
					throw new JspException("No bean found under attribute key "	+ name);
				}
				try {
					variable = PropertyUtils.getProperty(bean, property);
				} catch (InvocationTargetException e) {
					Throwable t = e.getCause();
					if (t == null)
						t = e;
					throw new JspException("Exception accessing property "
							+ property + " for bean " + name + ": "
							+ t.toString());
				} catch (Throwable t) {
					throw new JspException("Exception accessing property "
							+ property + " for bean " + name + ": "
							+ t.toString());
				}
			} else {
				variable = bean;
			}
		} else {
			throw new JspException("Attribute 'name' was not specified");
		}
		if (variable == null) {
			variable = ""; // Coerce null to a zero-length String
		}

		// Perform the appropriate comparison
		int result = 0;
		if (type == DOUBLE_COMPARE) {
			try {
				result = Double.compare(
						Double.parseDouble(variable.toString()), doubleValue);
			} catch (NumberFormatException e) {
				result = variable.toString().compareTo(value);
			}
		} else if (type == LONG_COMPARE) {
			try {
				long longVariable = Long.parseLong(variable.toString());
				if (longVariable < longValue)
					result = -1;
				else if (longVariable > longValue)
					result = +1;
			} catch (NumberFormatException e) {
				result = variable.toString().compareTo(value);
			}
		} else {
			result = variable.toString().compareTo(value);
		}

		if (result < 0)
			result = -1;
		else if (result > 0)
			result = +1;

		// Return true if the result matches either desired value
		return ((result == desired1) || (result == desired2));

	}

}
