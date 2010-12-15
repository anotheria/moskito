package net.java.dev.moskito.core.treshold;

public enum ThresholdStatus {
	GREEN,
	YELLOW,
	ORANGE,
	RED,
	PURPLE;
	
	private int priority;
	
	public boolean overrules(ThresholdStatus anotherStatus){
		return ordinal() > anotherStatus.ordinal();
	}
}
