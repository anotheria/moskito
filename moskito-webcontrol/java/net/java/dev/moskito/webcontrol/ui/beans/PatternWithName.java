package net.java.dev.moskito.webcontrol.ui.beans;

public class PatternWithName {
	
	private String pattern;
	private String fieldName;

	public PatternWithName(String name, String pattern) {
		this.fieldName = name;
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
