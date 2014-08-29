package net.anotheria.moskito.webui.journey.api;

import net.anotheria.util.sorter.SortType;

/**
 * Sort type class for the TracedCallDuplicateStepsAO.
 * @author lrosenberg
 *
 */
public final class TracedCallDuplicateStepsAOSortType extends SortType{
	
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
	
	public TracedCallDuplicateStepsAOSortType(){
		super(SORT_BY_DEFAULT);
	}

	public TracedCallDuplicateStepsAOSortType(int aSortBy){
		super(aSortBy);
	}

	public TracedCallDuplicateStepsAOSortType(int aSortBy, boolean aSortOrder){
		super(aSortBy, aSortOrder);
	}
}
