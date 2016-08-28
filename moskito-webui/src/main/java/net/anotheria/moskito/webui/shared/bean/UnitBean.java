package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.moskito.core.stats.TimeUnit;

import java.io.Serializable;

/**
 * This bean transports a timeunit into a jsp.
 * @author lrosenberg.
 *
 */
public class UnitBean implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2775817606042609460L;

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
