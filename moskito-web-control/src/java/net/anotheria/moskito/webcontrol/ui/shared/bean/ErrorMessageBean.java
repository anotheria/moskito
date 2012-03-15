package net.anotheria.moskito.webcontrol.ui.shared.bean;

import java.io.Serializable;

/**
 * Bean for publishing error message.
 * 
 * @author Alexandr Bolbat
 */
public class ErrorMessageBean implements Serializable {

	/**
	 * Basic serialVersionUID variable.
	 */
	private static final long serialVersionUID = 6847669472909518929L;

	/**
	 * Bean attribute name.
	 */
	public static final String BEAN_NAME = "mwc_error_msg";

	/**
	 * Field name.
	 */
	private String fieldName;

	/**
	 * Error text.
	 */
	private String errorText;

	/**
	 * Default constructor.
	 * 
	 * @param aFieldName
	 *            - field name
	 * @param aErrorText
	 *            - error text
	 */
	public ErrorMessageBean(final String aFieldName, final String aErrorText) {
		this.fieldName = aFieldName;
		this.errorText = aErrorText;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String aFieldName) {
		this.fieldName = aFieldName;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String aErrorText) {
		this.errorText = aErrorText;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorMessageBean [fieldName=");
		builder.append(fieldName);
		builder.append(", errorText=");
		builder.append(errorText);
		builder.append("]");
		return builder.toString();
	}

}
