package net.java.dev.moskito.webui.bean;

import net.java.dev.moskito.core.stats.TimeUnit;

/**
 * This bean transports a timeunit into a jsp.
 * @author lrosenberg.
 *
 */
public class UnitBean {
	/**
	 * The associated unit.
	 */
	private TimeUnit unit;
	
	public UnitBean(TimeUnit aUnit){
		unit = aUnit;
	}
	
	/**
	 * Returns the name of the unit.
	 * @return
	 */
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
