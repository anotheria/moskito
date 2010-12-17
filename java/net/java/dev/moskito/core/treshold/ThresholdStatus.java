package net.java.dev.moskito.core.treshold;

public enum ThresholdStatus {
	OFF,
	GREEN,
	YELLOW,
	ORANGE,
	RED,
	PURPLE;
	
	public boolean overrules(ThresholdStatus anotherStatus){
		return ordinal() > anotherStatus.ordinal();
	}
}
