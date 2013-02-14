package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.util.sorter.SortType;

public class ThresholdStatusAOSortType extends SortType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int BY_NAME = 1;
	public static final int BY_STATUS = 2;
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
