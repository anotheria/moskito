package net.java.dev.moskito.webui.bean;

import net.anotheria.util.sorter.SortType;

/**
 * Sort type class for the JourneyCallDuplicateStepBean.
 * @author lrosenberg
 *
 */
public final class JourneyCallDuplicateStepBeanSortType extends SortType{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sort by call string.
	 */
	public static final int SORT_BY_CALL = 1;
	/**
	 * Sort by number of positions.
	 */
	public static final int SORT_BY_POSITIONS = 2;
	/**
	 * Sort by timespent
	 */
	public static final int SORT_BY_TIMESPENT = 3;
	/**
	 * Sort by duration.
	 */
	public static final int SORT_BY_DURATION = 4;
	/**
	 * Default sort is by call. 
	 */
	public static final int SORT_BY_DEFAULT = SORT_BY_CALL;
	
	public JourneyCallDuplicateStepBeanSortType(){
		super(SORT_BY_DEFAULT);
	}

	public JourneyCallDuplicateStepBeanSortType(int aSortBy){
		super(aSortBy);
	}

	public JourneyCallDuplicateStepBeanSortType(int aSortBy, boolean aSortOrder){
		super(aSortBy, aSortOrder);
	}
}
