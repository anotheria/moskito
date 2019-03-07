package net.anotheria.moskito.webui.shared.api.filter;

/**
 * Matches when target ends with criteria.
 *
 * @author lrosenberg
 * @since 20.04.15 11:21
 */
public class EndsWithMatcher extends BaseMatcher implements Matcher {

	public EndsWithMatcher(String aCriteria){
		super(aCriteria);
	}

	@Override
	public boolean doesMatch(String target) {
		return target.endsWith(getCriteria());
	}
}
