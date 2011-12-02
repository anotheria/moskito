package net.java.dev.moskitodemo.annotation;

/**
 * Query property.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 12/1/11
 *         Time: 12:25 PM
 */
public class QueryProperty {
    /**
	 * svid.
	 */
	private static final long serialVersionUID = 2030752289719048811L;
	/**
	 * Name of the property.
	 */
	private String name;
	/**
	 * Value of the property.
	 */
	private Object value;
	private boolean unprepaireable;

	public QueryProperty(String aName, Object aValue){
		this(aName, aValue, false);
	}

	public QueryProperty(String aName, Object aValue, boolean anUnprepaireable){
		name = aName;
		value = aValue;
		unprepaireable = anUnprepaireable;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	@Override public String toString(){
		return getName() + "=" +getValue();
	}

	/**
	 * Returns the comparator operation for this query.
	 * @return
	 */
	public String getComparator(){
		return " = ";
	}

	public boolean doesMatch(Object o){
		return o== null ? value == null :
			o.equals(value);
	}

	public boolean unprepaireable(){
		return unprepaireable;
	}

	protected Object getOriginalValue(){
		return value;
	}
}
