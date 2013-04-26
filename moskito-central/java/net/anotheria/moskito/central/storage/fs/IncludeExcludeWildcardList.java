package net.anotheria.moskito.central.storage.fs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * IncludeExcludeExtendedList class.
 * 
 * @author dagafonov
 * 
 */
public class IncludeExcludeWildcardList extends IncludeExcludeList {

	/**
	 * 
	 */
	private List<Pattern> includePatterns = new ArrayList<Pattern>();
	
	/**
	 * 
	 */
	private List<Pattern> excludePatterns = new ArrayList<Pattern>();

	/**
	 * 
	 * @param include
	 * @param exclude
	 */
	public IncludeExcludeWildcardList(String include, String exclude) {
		super(include, exclude);
		fillPatterns(getIncludes(), includePatterns);
		fillPatterns(getExcludes(), excludePatterns);
	}
	
	private void fillPatterns(Set<String> set, List<Pattern> patterns) {
		for (String incl : set) {
			String s = incl;
			if (s.contains("*")) {
				s = s.replaceAll("\\*", ".*");
			}
			if (s.contains("?")) {
				s = s.replaceAll("\\?", ".{1}");
			}
			patterns.add(Pattern.compile("^"+s+"$"));
		}
	}

	@Override
	public boolean include(String query) {
		boolean exactMatch = super.include(query);
		if (exactMatch) {
			return true;
		}
		if (!exactMatch && getExcludes().contains(query)) {
			return false;
		}
		for (Pattern p : excludePatterns) {
			if (p.matcher(query).find()) {
				return false;
			}
		}
		for (Pattern p : includePatterns) {
			if (p.matcher(query).find()) {
				return true;
			}
		}
		return false;
	}

}
