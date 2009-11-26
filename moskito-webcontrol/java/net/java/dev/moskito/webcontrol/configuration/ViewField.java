package net.java.dev.moskito.webcontrol.configuration;

public class ViewField {
	private String fieldName;
	private String attributeName;

	public ViewField(String aFieldName, String anAttributeName){
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
	
	public String toString(){
		return fieldName +" :-> " +attributeName;
	}
}
