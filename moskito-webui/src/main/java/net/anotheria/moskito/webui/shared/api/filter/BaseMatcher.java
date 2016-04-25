package net.anotheria.moskito.webui.shared.api.filter;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 11:21
 */
public abstract class BaseMatcher {

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
