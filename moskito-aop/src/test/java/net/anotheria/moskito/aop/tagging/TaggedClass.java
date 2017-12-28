package net.anotheria.moskito.aop.tagging;

import net.anotheria.moskito.aop.annotation.TagParameter;
import net.anotheria.moskito.aop.annotation.TagReturnValue;

/**
 * @author esmakula
 */
public class TaggedClass {

	@TagReturnValue(name = "tagReturn")
	public String tagReturnValue() {
		return "value";
	}


	public void tagParameter(@TagParameter(name = "tagParamSingle") String param) {
	}

	public void tagMultipleParameters(@TagParameter(name = "tagMultipleParamFirst") String paramFirst, @TagParameter(name = "tagMultipleParamSecond") int paramSecond, String paramThird) {
	}
}
