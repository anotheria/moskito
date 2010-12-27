package net.java.dev.moskito.core.treshold;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

public enum ThresholdStatus implements IComparable{
	OFF,
	GREEN,
	YELLOW,
	ORANGE,
	RED,
	PURPLE;
	
	public boolean overrules(ThresholdStatus anotherStatus){
		return ordinal() > anotherStatus.ordinal();
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareInt(ordinal(), ((ThresholdStatus)anotherObject).ordinal());
	}
}
