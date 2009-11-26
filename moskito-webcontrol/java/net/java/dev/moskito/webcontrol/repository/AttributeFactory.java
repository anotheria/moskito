package net.java.dev.moskito.webcontrol.repository;

public final class AttributeFactory {
	public static Attribute create(AttributeType type, String name, String value){
		switch(type){
		case LONG:
			return new LongAttribute(name, value);
		}
		throw new IllegalArgumentException("Unsupported attribute type: "+type);
	}
	
	
	private AttributeFactory(){}
}
