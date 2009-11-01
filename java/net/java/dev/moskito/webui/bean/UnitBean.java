package net.java.dev.moskito.webui.bean;

import net.java.dev.moskito.core.stats.TimeUnit;

public class UnitBean {
	private TimeUnit unit;
	
	public UnitBean(TimeUnit aUnit){
		unit = aUnit;
	}
	
	public String getUnitName() {
		return unit.toString();
	}
	
	@Override public boolean equals(Object o){
		return (o instanceof UnitBean) ? 
				unit==((UnitBean)o).unit : false;
	}
	
	@Override public int hashCode() {
		  assert false : "hashCode not designed";
		  return 42; // any arbitrary constant will do 
	}
	
	public TimeUnit getUnit(){
		return unit;
	}
	
}
