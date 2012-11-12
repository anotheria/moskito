package net.anotheria.moskito.webcontrol.repository;

import java.text.NumberFormat;

public class IntAttribute extends NumberAttribute<Integer> {

	private int value;

	public IntAttribute(String aName, int aValue) {
		super(aName);
		value = aValue;
	}

	public IntAttribute(String aName, String aValue) {
		this(aName, parseInt(aValue));
	}

	@Override
	public Integer getValue() {
		return Integer.valueOf(value);
	}

	private static Integer parseInt(String value) {
		if ("NoR".equalsIgnoreCase(value)) {
			return 0;
		}
		try {
			NumberFormat nf = NumberFormat.getIntegerInstance();
			Long res = (Long) nf.parse(value);
			return res.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
