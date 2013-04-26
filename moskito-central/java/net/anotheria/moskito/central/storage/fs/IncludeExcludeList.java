package net.anotheria.moskito.central.storage.fs;

import net.anotheria.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * IncludeExcludeList class.
 * 
 * @author lrosenberg
 * @since 25.03.13 10:30
 */
public class IncludeExcludeList {

	/**
	 * 
	 */
	private boolean includeAll = false;

	/**
	 * Include {@link Set}.
	 */
	private Set<String> includes = new HashSet<String>();

	/**
	 * Exclude {@link Set}.
	 */
	private Set<String> excludes = new HashSet<String>();

	/**
	 * 
	 * @param include
	 * @param exclude
	 */
	public IncludeExcludeList(String include, String exclude) {
		if (include != null && include.trim().equals("*"))
			includeAll = true;
		if (include == null)
			include = "";
		if (exclude == null)
			exclude = "";
		String ii[] = StringUtils.tokenize(include.trim(), ',');
		for (String i : ii)
			includes.add(i.trim());
		String ee[] = StringUtils.tokenize(exclude.trim(), ',');
		for (String e : ee)
			excludes.add(e.trim());
	}
	
	public Set<String> getIncludes() {
		return includes;
	}
	
	public Set<String> getExcludes() {
		return excludes;
	}

	/**
	 * Checks on availability query in include, exclude {@link Set}'s.
	 * 
	 * @param query
	 * @return boolean
	 */
	public boolean include(String query) {
		if (excludes.contains(query)) {
			return false;
		}
		return includeAll || includes.contains(query);
	}
	
	@Override
	public String toString() {
		return "All: " + includeAll + ", incl: " + includes + ", excl: " + excludes;
	}
}
