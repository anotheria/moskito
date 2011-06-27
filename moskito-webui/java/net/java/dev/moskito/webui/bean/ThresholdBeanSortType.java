package net.java.dev.moskito.webui.bean;

import net.anotheria.util.sorter.SortType;

public class ThresholdBeanSortType extends SortType {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int BY_NAME = 1;
	public static final int BY_STATUS = 2;
	public static final int BY_CHANGE = 3;
	
	public ThresholdBeanSortType(){
		super(BY_NAME, ASC);
	}
	
	public ThresholdBeanSortType(int method, boolean direction){
		super(method, direction);
	}
	
	public static ThresholdBeanSortType create(int aValue){
		boolean direction = aValue == BY_NAME ? ASC : DESC;
		return new ThresholdBeanSortType(aValue, direction);
	}
}
