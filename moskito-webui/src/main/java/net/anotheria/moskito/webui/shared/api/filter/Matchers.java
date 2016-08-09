package net.anotheria.moskito.webui.shared.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 09:23
 */
public final class Matchers {

	private static Logger log = LoggerFactory.getLogger(Matchers.class);

	public static final Matcher createMatcher(String criteria){
		if (criteria == null || criteria.isEmpty())
			throw new IllegalArgumentException("Empty criteria");
		if (containsAsteriskInTheMiddle(criteria))
			log.warn("Matcher definition for "+criteria+" contains asterisk in the middle, which is not supported yet");
		if (criteria.startsWith("*")){
			if (criteria.endsWith("*")){
				return new SubstringMatcher(criteria.substring(1, criteria.length()-1));
			}else{
				return new StartsWithMatcher(criteria.substring(1));
			}
		}

		if (criteria.endsWith("*"))
			return new EndsWithMatcher(criteria.substring(0, criteria.length()-1));

		return new EqualsMatcher(criteria);

	}

	private static boolean containsAsteriskInTheMiddle(String str){
		if (str==null || str.isEmpty())
			return false;
		if (str.length() < 2)
			return false;
		str = str.substring(1, str.length()-1);
		return str.indexOf('*') != -1;
	}
}
