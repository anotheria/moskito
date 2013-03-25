package net.anotheria.moskito.central.storage.fs;

import net.anotheria.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.13 10:30
 */
public class IncludeExcludeList {

	private boolean includeAll = false;
	private Set<String> includes = new HashSet<String>();
	private Set<String> excludes = new HashSet<String>();

	public IncludeExcludeList(String include, String exclude){
		if (include!=null && include.trim().equals("*"))
			includeAll = true;
		if (include==null)
			include = "";
		if (exclude==null)
			exclude = "";
		String ii[] = StringUtils.tokenize(include.trim(), ',');
		for (String i: ii)
			includes.add(i.trim());
		String ee[] = StringUtils.tokenize(exclude.trim(), ',');
		for (String e: ee)
			excludes.add(e.trim());
	}

	public boolean include(String query){
		if (excludes.contains(query)){
			return false;
		}
		return includeAll || includes.contains(query);
	}

	@Override public String toString(){
		return "All: "+includeAll+", incl: "+includes+", excl: "+excludes;
	}
}
