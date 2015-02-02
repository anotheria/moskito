package net.anotheria.moskito.sql.callingAspect;


/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 11:21 AM
 *         To change this template use File | Settings | File Templates.
 */
public class MatcherValueBuilder {

	protected int type;
	protected String value;
	protected String matcherId;

	/**
	 * Sets the value of the type attribute.
	 */
	public MatcherValueBuilder type(int aValue){
		type = aValue;
		return this;
	}

	/**
	 * Sets the value of the value attribute.
	 */
	public MatcherValueBuilder value(String aValue){
		value = aValue;
		return this;
	}

	/**
	 * Sets the value of the matcherId attribute.
	 */
	public MatcherValueBuilder matcherId(String aValue){
		matcherId = aValue;
		return this;
	}


	public MatcherValue build(){
		return MatcherValueFactory.createMatcherValue(this);
	}
}
