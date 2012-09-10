package net.java.dev.moskito.sql.callingAspect;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 11:22 AM
 *         To change this template use File | Settings | File Templates.
 */
public class MatcherValueFactory {
    public static MatcherValue createMatcherValue(MatcherValue template){
		return new MatcherValueVO((MatcherValueVO)template);
	}

	public static MatcherValue createMatcherValue(){
		return new MatcherValueVO("");
	}

    static MatcherValue createMatcherValue(MatcherValueBuilder builder) {
        return new MatcherValueVO(builder);
    }

	public static MatcherValue createMatcherValueForImport(String anId){
		return new MatcherValueVO(anId);
	}

	/**
	 * For internal use only!
	 */
	public static MatcherValue createMatcherValue(String anId){
		return new MatcherValueVO(anId);
	}
}
