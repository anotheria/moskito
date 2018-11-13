package net.anotheria.moskito.webui.shared.api.filter;

/**
 * Matches when target contains criteria.
 *
 * @author lrosenberg
 * @since 20.04.15 09:24
 */
public class SubstringMatcher extends BaseMatcher implements Matcher {

	public SubstringMatcher(String aCriteria){
		super(aCriteria);
	}

	@Override
	public boolean doesMatch(String target) {
		return target.contains(getCriteria());
	}

}
