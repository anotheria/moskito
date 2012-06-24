package net.java.dev.moskito.webui;

import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.AnalyzeProducerCallsBeanSortType;

/**
 * This bean, once initialized, holds current selection data in the thread, to prevent a lot of parameter passing between functions.
 * @author lrosenberg
 *
 */
public class CurrentSelection{
	/**
	 * Currently selected time unit for time manipulation.
	 */
	private TimeUnit currentTimeUnit;
	/**
	 * Name of currently selected interval.
	 */
	private String currentIntervalName;
	/**
	 * Currently selected sort type for analyze beans.
	 */
	private AnalyzeProducerCallsBeanSortType analyzeProducerCallsSortType;
	
	public static CurrentSelection get(){
		return selection.get();
	}
	
	private void reset(){
		currentTimeUnit = null;
		currentIntervalName = null;
		analyzeProducerCallsSortType = new AnalyzeProducerCallsBeanSortType();
	}
	
	public static CurrentSelection resetAndGet(){
		CurrentSelection current = selection.get();
		current.reset();
		return current;
	}
	
	/**
	 * The thread local state. 
	 */
	private final static ThreadLocal<CurrentSelection> selection = new ThreadLocal<CurrentSelection>(){
		@Override
		protected synchronized CurrentSelection initialValue() {
			return new CurrentSelection();
		}

	};

	public TimeUnit getCurrentTimeUnit() {
		return currentTimeUnit;
	}

	public void setCurrentTimeUnit(TimeUnit currentTimeUnit) {
		this.currentTimeUnit = currentTimeUnit;
	}

	public String getCurrentIntervalName() {
		return currentIntervalName;
	}

	public void setCurrentIntervalName(String currentIntervalName) {
		this.currentIntervalName = currentIntervalName;
	}

	public AnalyzeProducerCallsBeanSortType getAnalyzeProducerCallsSortType() {
		return analyzeProducerCallsSortType;
	}

	public void setAnalyzeProducerCallsSortType(
			AnalyzeProducerCallsBeanSortType analyzeProducerCallsSortType) {
		this.analyzeProducerCallsSortType = analyzeProducerCallsSortType;
	}
}
