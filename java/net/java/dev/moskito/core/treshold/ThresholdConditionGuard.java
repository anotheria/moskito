package net.java.dev.moskito.core.treshold;

public interface ThresholdConditionGuard {
	ThresholdStatus getNewStatusOnUpdate(String previousValue, String newValue, ThresholdStatus currentStatus, Threshold threshold);
}
