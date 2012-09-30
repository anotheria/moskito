package net.anotheria.moskito.core.treshold;

public interface ThresholdConditionGuard {
	ThresholdStatus getNewStatusOnUpdate(String previousValue, String newValue, ThresholdStatus currentStatus, Threshold threshold);
}
