package net.anotheria.moskito.webui.shared.api.filter;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 11:21
 */
public class EqualsMatcher extends BaseMatcher implements Matcher {

	public EqualsMatcher(String aCriteria){
		super(aCriteria);
	}

	@Override
	public boolean doesMatch(String target) {
		return target.equals(getCriteria());
	}

}
