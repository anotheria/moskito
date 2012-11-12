package net.anotheria.moskito.webcontrol.repository;

public class StringAttribute extends Attribute {

	private String value;

	public StringAttribute(String name, String aValue) {
		super(name);
		value = aValue;
	}

	@Override
	public String getValueString() {
		return getValue();
	}

	@Override
	public String getValue() {
		return value;
	}

}
