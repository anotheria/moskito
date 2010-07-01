package net.java.dev.moskito.webcontrol.configuration;

import java.util.List;

import net.java.dev.moskito.webcontrol.repository.ColumnType;
import net.java.dev.moskito.webcontrol.repository.TotalFormulaType;

public class ViewField {

	private String fieldName;
	private String attributeName;
	private ColumnType type;
	private String javaType;
	private Boolean visible;
	private String path;
	private TotalFormulaType total;

	private List<String> inputs;

	public ViewField(String aFieldName, String anAttributeName, ColumnType type, String javaType, Boolean visible, String path) {
		this.fieldName = aFieldName;
		this.attributeName = anAttributeName;
		this.type = type;
		this.javaType = javaType;
		this.visible = visible;
		this.path = path;
	}

	public ViewField(String aFieldName, String anAttributeName) {
		fieldName = aFieldName;
		attributeName = anAttributeName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String toString() {
		return fieldName + " :-> " + attributeName;
	}

	public ColumnType getType() {
		return type;
	}

	public void setType(ColumnType type) {
		this.type = type;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public TotalFormulaType getTotal() {
		return total;
	}

	public void setTotal(TotalFormulaType total) {
		this.total = total;
	}

}
