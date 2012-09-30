package net.anotheria.moskito.core.snapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 30.09.12 15:25
 */
public class StatSnapshot {
	private String name;

	private Map<String, String> values = new HashMap<String, String>();

	public StatSnapshot(String aName){
		name = aName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String name, String value){
		values.put(name, value);
	}

	public Map<String, String> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return getName()+": "+getValues();
	}

	public String getValue(String valueName){
		return values.get(valueName);
	}
}


