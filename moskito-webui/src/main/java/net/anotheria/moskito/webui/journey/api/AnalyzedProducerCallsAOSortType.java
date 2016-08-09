package net.anotheria.moskito.webui.journey.api;

import net.anotheria.util.sorter.SortType;

public class AnalyzedProducerCallsAOSortType extends SortType{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Sort by name.
	 */
	public static final int SORT_BY_NAME = 1;
	/**
	 * Sort by call count.
	 */
	public static final int SORT_BY_CALLS = 2;
	/**
	 * Sort by call duration.
	 */
	public static final int SORT_BY_DURATION = 3;
	/**
	 * Default sort type.
	 */
	public static final int SORT_BY_DEFAULT = SORT_BY_NAME;
	
	public AnalyzedProducerCallsAOSortType(){
		super(SORT_BY_DEFAULT, true);
	}

	public AnalyzedProducerCallsAOSortType(int sortType, boolean sortOrder){
		super(sortType, sortOrder);
	}
	
	public static final AnalyzedProducerCallsAOSortType fromStrings(String aSortByString, String aSortOrderString){
		if (aSortByString==null || aSortByString.isEmpty())
			throw new IllegalArgumentException("Empty sort by not allowed");
		if (aSortOrderString==null || aSortOrderString.isEmpty())
			throw new IllegalArgumentException("Empty sort order not allowed");
		int sortBy = -1;
		if (aSortByString.equalsIgnoreCase("duration"))
			sortBy = SORT_BY_DURATION;
		if (aSortByString.equalsIgnoreCase("calls"))
			sortBy = SORT_BY_CALLS;
		if (aSortByString.equalsIgnoreCase("name"))
			sortBy = SORT_BY_NAME;
		if (sortBy==-1)
			throw new IllegalArgumentException("Unexpected value for sortBy: "+sortBy);
		return new AnalyzedProducerCallsAOSortType(sortBy, aSortOrderString.equalsIgnoreCase("ASC"));
	}
}
