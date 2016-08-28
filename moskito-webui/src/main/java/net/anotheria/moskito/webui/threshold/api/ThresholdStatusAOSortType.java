package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.util.sorter.SortType;

/**
 * Sorting help class (SortType) for ThresholdStatusAO.
 */
public class ThresholdStatusAOSortType extends SortType {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2087413182297900163L;
	/**
	 * Sort by name.
	 */
	public static final int BY_NAME = 1;
	/**
	 * Sort by status.
	 */
	public static final int BY_STATUS = 2;
	/**
	 * Sort by change dieection.
	 */
	public static final int BY_CHANGE = 3;
	
	public ThresholdStatusAOSortType(){
		super(BY_NAME, ASC);
	}
	
	public ThresholdStatusAOSortType(int method, boolean direction){
		super(method, direction);
	}
	
	public static ThresholdStatusAOSortType create(int aValue){
		boolean direction = aValue == BY_NAME ? ASC : DESC;
		return new ThresholdStatusAOSortType(aValue, direction);
	}
}
