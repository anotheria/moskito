package net.anotheria.moskito.webcontrol.repository;

import net.anotheria.moskito.webcontrol.guards.Condition;

public abstract class Attribute {
	private String name;
	private Condition condition = Condition.DEFAULT;
	
	Attribute(String aName){
		name = aName;
	}
	
	@Override public boolean equals(Object anotherAttribute){
		return anotherAttribute instanceof Attribute && 
			name.equals(((Attribute)anotherAttribute).name);
	}
	
	@Override public int hashCode(){
		return name.hashCode();
	}
	
	public abstract String getValueString();
	
	public abstract Object getValue();
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return getName()+" = "+getValueString()+", cond = "+getCondition().getColor();
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
}
