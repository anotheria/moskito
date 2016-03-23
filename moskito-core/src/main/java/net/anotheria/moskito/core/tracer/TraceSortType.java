package net.anotheria.moskito.core.tracer;

import net.anotheria.util.sorter.SortType;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.16 18:00
 */
public class TraceSortType extends SortType{
	public static final int SORT_BY_ID = 1;
	public static final int SORT_BY_DURATION = 2;

	public TraceSortType(){
		super(SORT_BY_ID);
	}

	public TraceSortType(int sortBy, boolean sortOrder){
		super(sortBy, sortOrder);
	}
}
