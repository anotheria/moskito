package net.anotheria.moskito.webui.shared.api.filter;

/**
 * Base class for matchers.
 *
 * @author lrosenberg
 * @since 20.04.15 11:21
 */
public abstract class BaseMatcher {

	/**
	 * Criteria to match.
	 */
	private String criteria;

	public BaseMatcher(String aCriteria){
		criteria = aCriteria;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+": "+getCriteria();
	}
}
