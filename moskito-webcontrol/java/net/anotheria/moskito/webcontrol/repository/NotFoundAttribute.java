package net.anotheria.moskito.webcontrol.repository;

public class NotFoundAttribute extends Attribute {

	public NotFoundAttribute(String name) {
		super(name);
	}

	@Override public String getValueString() {
		return "n.a.";
	}

	@Override public Void getValue() {
		//throw new IllegalAccessError("Attribute was not found, therefore it cannot provide a value!");
		return null;
	}

}
